
// EC2ConfigListLoader.java --
//
// EC2ConfigListLoader.java is part of ElectricCommander.
//
// Copyright (c) 2005-2012 Electric Cloud, Inc.
// All rights reserved.
//

package ecplugins.s3.client;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NonNls;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

import com.electriccloud.commander.client.ChainedCallback;
import com.electriccloud.commander.client.domain.Property;
import com.electriccloud.commander.client.requests.GetPropertyRequest;
import com.electriccloud.commander.client.responses.CommanderError;
import com.electriccloud.commander.client.responses.PropertyCallback;
import com.electriccloud.commander.client.util.StringUtil;
import com.electriccloud.commander.gwt.client.Component;
import com.electriccloud.commander.gwt.client.requests.CgiRequestProxy;

import ecinternal.client.HasErrorPanel;
import ecinternal.client.Loader;

import static ecinternal.client.InternalComponentBaseFactory.getPluginName;

public class S3ConfigListLoader
    extends Loader
{

    //~ Instance fields --------------------------------------------------------

    private final S3ConfigList   m_configList;
    private final CgiRequestProxy m_cgiRequestProxy;
    private String                m_editorName;

    //~ Constructors -----------------------------------------------------------

    public S3ConfigListLoader(
            S3ConfigList   configList,
            Component       component,
            ChainedCallback callback)
    {
        super(component, callback);
        m_configList      = configList;
        m_cgiRequestProxy = new CgiRequestProxy(getPluginName(), "s3.cgi");
    }

    //~ Methods ----------------------------------------------------------------

    @Override public void load()
    {
        Map<String, String> cgiParams = new HashMap<String, String>();

        cgiParams.put("cmd", "getCfgList");
        loadConfigs(cgiParams);
    }

    @SuppressWarnings("OverlyComplexAnonymousInnerClass")
    private void loadConfigs(Map<String, String> cgiParams)
    {

        try {
            String request = m_cgiRequestProxy.issueGetRequest(cgiParams,
                    new RequestCallback() {
                        @Override public void onError(
                                @SuppressWarnings(
                                    "AnonymousClassVariableHidesContainingMethodVariable"
                                ) Request request,
                                Throwable exception)
                        {

                            // noinspection HardCodedStringLiteral
                            ((HasErrorPanel) m_component).addErrorMessage(
                                "Error loading S3 configuration list: ",
                                exception);
                        }

                        @Override public void onResponseReceived(
                                @SuppressWarnings(
                                    "AnonymousClassVariableHidesContainingMethodVariable"
                                ) Request request,
                                Response response)
                        {
                            @NonNls String responseString = response.getText();

                            // if HTML returned we never made it to the CGI
                            Boolean isHtml = responseString.contains(
                                    "DOCTYPE HTML");
                            String  error;

                            if (isHtml) {
                                error = responseString;
                            }
                            else {
                                error = m_configList.parseResponse(
                                        responseString);
                            }

                            if (m_component.getLog()
                                           .isDebugEnabled()) {
                                m_component.getLog()
                                           .debug(
                                               "Recieved CGI response: "
                                               + responseString
                                               + " isHTML:" + isHtml
                                               + " error:" + error);
                            }

                            if (error == null) {

                                if (StringUtil.isEmpty(m_editorName)
                                        || m_configList.isEmpty()) {

                                    // We're done!
                                    if (m_callback != null) {
                                        m_callback.onComplete();
                                    }
                                }
                                else {
                                    loadEditors();
                                }
                            }
                            else {
                                ((HasErrorPanel) m_component).addErrorMessage(
                                    error);
                            }
                        }
                    });

            if (m_component.getLog()
                           .isDebugEnabled()) {
                m_component.getLog()
                           .debug("Issued CGI request: " + request);
            }
        }
        catch (RequestException e) {

            if (m_component instanceof HasErrorPanel) {

                // noinspection HardCodedStringLiteral
                ((HasErrorPanel) m_component).addErrorMessage(
                    "Error loading S3 configuration list: ", e);
            }
            else {
                m_component.getLog()
                           .error(e);
            }
        }
    }

    private void loadEditors()
    {
        GetPropertyRequest request =
            m_requestFactory.createGetPropertyRequest();

        request.setPropertyName("/plugins/EC-S3/project/ui_forms/"
                + m_editorName);
        request.setExpand(false);
        request.setCallback(new EditorLoaderCallback("s3cfg"));
        m_requestManager.doRequest(new ChainedCallback() {
                @Override public void onComplete()
                {

                    // We're done!
                    if (m_callback != null) {
                        m_callback.onComplete();
                    }
                }
            }, request);
    }

    public void setEditorName(String editorName)
    {
        m_editorName = editorName;
    }

    //~ Inner Classes ----------------------------------------------------------

    public class EditorLoaderCallback
        implements PropertyCallback
    {

        //~ Instance fields ----------------------------------------------------

        private final String m_configPlugin;

        //~ Constructors -------------------------------------------------------

        public EditorLoaderCallback(@NonNls String configPlugin)
        {
            m_configPlugin = configPlugin;
        }

        //~ Methods ------------------------------------------------------------

        @Override public void handleError(CommanderError error)
        {

            if (m_component instanceof HasErrorPanel) {
                ((HasErrorPanel) m_component).addErrorMessage(error);
            }
            else {
                m_component.getLog()
                           .error(error);
            }
        }

        @Override public void handleResponse(Property response)
        {

            if (m_component.getLog()
                           .isDebugEnabled()) {
                m_component.getLog()
                           .debug("Commander getProperty request returned: "
                               + response);
            }

            if (response != null) {
                String value = response.getValue();

                if (!StringUtil.isEmpty(value)) {
                    m_configList.setEditorDefinition(m_configPlugin, value);

                    return;
                }
            }

            // There was no property value found in the response
            @SuppressWarnings({"HardCodedStringLiteral", "StringConcatenation"})
            String errorMsg = "Editor '" + m_editorName
                    + "' not found for S3 plugin '" + m_configPlugin + "'";

            if (m_component instanceof HasErrorPanel) {
                ((HasErrorPanel) m_component).addErrorMessage(errorMsg);
            }
            else {
                m_component.getLog()
                           .error(errorMsg);
            }
        }
    }
}
