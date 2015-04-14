package com.electriccloud.commander.gwt.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ResourcePrototype;

public class BundledResources_default_StaticClientBundleGenerator implements com.electriccloud.commander.gwt.client.ui.BundledResources {
  private static BundledResources_default_StaticClientBundleGenerator _instance0 = new BundledResources_default_StaticClientBundleGenerator();
  private void cssInitializer() {
    css = new com.electriccloud.commander.gwt.client.ui.BundledStyles() {
      private boolean injected;
      public boolean ensureInjected() {
        if (!injected) {
          injected = true;
          com.google.gwt.dom.client.StyleInjector.inject(getText());
          return true;
        }
        return false;
      }
      public String getName() {
        return "css";
      }
      public String getText() {
        return com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ? ((".GKVJYERBFF,.GKVJYERBFF a,.GKVJYERBFF a:hover,a.GKVJYERBFF,a.GKVJYERBFF:hover{background:" + ("transparent"+ " " +"none"+ " " +"repeat"+ " " +"scroll"+ " " +"100%")  + ";}table.formBuilder .gwt-Label{padding-left:" + ("10px")  + ";}.formBuilder-textAreaLabel,.GKVJYERBOG{padding-top:" + ("11px")  + ";}.formBuilder-textEntryLabel,.GKVJYERBPG{padding-top:" + ("8px")  + ";}.formBuilder-checkboxLabel,.GKVJYERBH{padding-top:" + ("3px")  + ";}.formBuilder-radioLabel,.GKVJYERBJF,.formBuilder-dropdownLabel,.GKVJYERBBB{padding-top:" + ("8px")  + ";}.formBuilder-label,.formBuilder-required{width:" + ("0")  + ";}td.GKVJYERBHB{width:" + ("148px")  + ";}div.GKVJYERBHB{font-size:" + ("110%")  + ";font-weight:" + ("bold")  + ";color:") + (("#687291")  + ";margin-top:" + ("6px")  + ";}tr.GKVJYERBMB{border-bottom-color:" + ("#ccc")  + ";border-bottom-style:" + ("dotted")  + ";border-bottom-width:" + ("1px")  + ";}img.GKVJYERBL{margin-top:" + ("10px")  + ";margin-left:" + ("10px")  + ";vertical-align:" + ("middle")  + ";}.GKVJYERBJ{background-color:" + ("#efefef")  + ";border-color:" + ("#666")  + ";border-width:" + ("1px"+ " " +"1px"+ " " +"3px"+ " " +"3px") ) + (";border-style:" + ("solid"+ " " +"solid"+ " " +"solid"+ " " +"solid")  + ";}.GKVJYERBK{font-weight:" + ("normal")  + ";border:" + ("1px"+ " " +"solid"+ " " +"#efefef")  + ";background-color:" + ("#efefef")  + ";padding:" + ("3px")  + ";}.GKVJYERBK:hover{background-color:" + ("#def")  + ";border:" + ("1px"+ " " +"solid"+ " " +"#c1e0ff")  + ";}.GKVJYERBMF{padding-right:" + ("5px")  + ";padding-top:" + ("10px")  + ";}.formTable-label,.formTable-textValue{padding-left:" + ("5px")  + ";padding-top:") + (("10px")  + ";vertical-align:" + ("top")  + ";width:" + ("150px")  + ";}.formTable-label>*{float:" + ("left")  + ";}.GKVJYERBFC{width:" + ("100%")  + ";color:" + ("#696969")  + ";}.GKVJYERBHC{float:" + ("none")  + ";}.GKVJYERBGC{display:" + ("none")  + ";}.GKVJYERBND{cursor:" + ("help")  + ";border-bottom-color:" + ("#d8d8d8")  + ";border-bottom-width:" + ("thin") ) + (";border-bottom-style:" + ("dashed")  + ";}.formBuilder-requiredLabel,.GKVJYERBNF{padding-right:" + ("5px")  + ";}.GKVJYERBDE .Caption{color:" + ("#687291")  + ";font-weight:" + ("bold")  + ";font-size:" + ("110%")  + ";}.form-value input,.form-value textarea{margin-top:" + ("8px")  + ";padding:" + ("2px")  + ";}.form-value select{margin-top:" + ("8px")  + ";}.form-value input.gwt-TextBox{width:" + ("100%")  + ";}.form-value textarea{width:" + ("100%")  + ";vertical-align:") + (("middle")  + ";}.form-value{padding-left:" + ("1em")  + ";}.GKVJYERBI td.form-value input.gwt-TextBox{width:" + ("17.8em")  + ";}.gwt-SuggestBoxPopup .item{cursor:" + ("default")  + ";padding:" + ("2px"+ " " +"6px")  + ";}.gwt-Anchor{cursor:" + ("pointer")  + ";}.grayDecoratorPanel{margin-bottom:" + ("10px")  + ";}.grayDecoratorPanel .topLeft{background-image:" + ("url(/commander/lib/images/cornergray_tl.gif)")  + ";background-position:" + ("right"+ " " +"top")  + ";background-repeat:" + ("no-repeat")  + ";height:" + ("6px") ) + (";width:" + ("5px")  + ";}.grayDecoratorPanel .topCenter{background-color:" + ("#eeeff2")  + ";background-image:" + ("url(/commander/lib/images/cornergray_tm.gif)")  + ";background-position:" + ("center"+ " " +"top")  + ";background-repeat:" + ("repeat-x")  + ";height:" + ("6px")  + ";width:" + ("auto")  + ";}.grayDecoratorPanel .topRight{background-image:" + ("url(/commander/lib/images/cornergray_tr.gif)")  + ";background-position:" + ("left"+ " " +"top")  + ";background-repeat:" + ("no-repeat")  + ";height:") + (("6px")  + ";width:" + ("5px")  + ";}.grayDecoratorPanel .middleLeft{background-color:" + ("#eeeff2")  + ";border-right:" + ("1px"+ " " +"solid"+ " " +"#d1d1d1")  + ";padding:" + ("0"+ " " +"0"+ " " +"5px"+ " " +"0")  + ";font-size:" + ("100%")  + ";}.grayDecoratorPanel .middleCenter{background-color:" + ("#eeeff2")  + ";padding:" + ("0")  + ";font-size:" + ("100%")  + ";}.grayDecoratorPanel .middleRight{background-color:" + ("#eeeff2")  + ";border-left:" + ("1px"+ " " +"solid"+ " " +"#d1d1d1") ) + (";padding:" + ("0"+ " " +"0"+ " " +"5px"+ " " +"0")  + ";font-size:" + ("100%")  + ";}.grayDecoratorPanel .bottomLeft{background-image:" + ("url(/commander/lib/images/cornergray_bl.gif)")  + ";background-position:" + ("right"+ " " +"bottom")  + ";background-repeat:" + ("no-repeat")  + ";height:" + ("6px")  + ";width:" + ("5px")  + ";}.grayDecoratorPanel .bottomCenter{background-color:" + ("#eeeff2")  + ";background-image:" + ("url(/commander/lib/images/cornergray_bm.gif)")  + ";background-position:" + ("center"+ " " +"bottom")  + ";background-repeat:") + (("repeat-x")  + ";height:" + ("6px")  + ";}.grayDecoratorPanel .bottomRight{background-image:" + ("url(/commander/lib/images/cornergray_br.gif)")  + ";background-position:" + ("left"+ " " +"bottom")  + ";background-repeat:" + ("no-repeat")  + ";height:" + ("6px")  + ";width:" + ("5px")  + ";}.grayDecoratorPanel .borderLeft{border-right:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";}.gwt-PopupPanel input{border:" + ("none")  + ";padding:" + ("0")  + ";}.gwt-PopupPanel input:focus{background-color:" + ("#fff") ) + (";border:" + ("none")  + ";}.gwt-TextBox,input.gwt-SuggestBox,.gwt-PasswordTextBox{width:" + ("200px")  + ";}.inlinediv{display:" + ("inline")  + ";}button.smallButton{margin:" + ("0"+ " " +"0"+ " " +"0"+ " " +"0")  + ";padding:" + ("0"+ " " +"3px")  + ";}div.gwt-SuggestBoxPopup{border:" + ("1px"+ " " +"solid"+ " " +"#d1d1d1")  + ";}div.link:hover{color:" + ("#000")  + ";text-decoration:" + ("none")  + ";margin-left:" + ("0")  + ";padding:" + ("1px")  + ";background-image:") + (("url(/commander/lib/images/dottedBorder1.gif)")  + ";background-position:" + ("bottom")  + ";background-repeat:" + ("repeat-x")  + ";display:" + ("inline")  + ";}.gwt-SuggestBoxPopup .suggestPopupContent{background:" + ("white")  + ";}.gwt-SuggestBoxPopup .item-selected{background:" + ("none"+ " " +"repeat"+ " " +"scroll"+ " " +"0"+ " " +"0"+ " " +"#d1d1d1")  + ";}.gwt-SuggestBoxPopup .suggestPopupTopLeftInner,.gwt-SuggestBoxPopup .suggestPopupTopRightInner,.gwt-SuggestBoxPopup .suggestPopupBottomLeftInner,.gwt-SuggestBoxPopup .suggestPopupBottomRightInner{height:" + ("inherit")  + ";width:" + ("inherit")  + ";}table.wideForm_diagnostic,table.form_diagnostic{color:" + ("#c00")  + ";font-size:" + ("100%")  + ";}table.form_diagnostic img.icon{vertical-align:" + ("top") ) + (";padding-left:" + ("3px")  + ";margin-top:" + ("1px")  + ";}table.form_diagnostic a,table.wideForm_diagnostic a{color:" + ("#c00")  + ";}table.wideForm>tbody>tr>td:first-child{width:" + ("150px")  + ";vertical-align:" + ("top")  + ";text-align:" + ("left")  + ";}table.wideForm>tbody>tr>td[align=\"left\"]:first-child{text-align:" + ("right")  + ";}table.wideForm_diagnostic img.icon{vertical-align:" + ("top")  + ";padding-left:" + ("3px")  + ";margin-top:" + ("1px")  + ";}.dialogTopCenter{border-bottom:") + (("1px"+ " " +"solid"+ " " +"#d1d1d1")  + ";}.gwt-DialogBox .Caption{background:" + ("none")  + ";border:" + ("none")  + ";}.dialogMiddleLeftInner,.dialogMiddleRightInner,html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem .tabMiddleRightInner,html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem .tabMiddleLeftInner{width:" + ("1px")  + ";}.dialogMiddleCenter{background:" + ("none"+ " " +"repeat"+ " " +"scroll"+ " " +"0"+ " " +"0"+ " " +"#fff")  + ";}td.VerticalTabPanelRight{border:" + ("1px"+ " " +"solid"+ " " +"#d1d1d1")  + ";-moz-border-radius:" + ("7px"+ " " +"7px"+ " " +"7px"+ " " +"0")  + ";-webkit-border-radius:" + ("7px")  + ";border-bottom-right-radius:" + ("0")  + ";border-bottom-left-radius:" + ("7px")  + ";border-top-right-radius:" + ("7px") ) + (";border-top-left-radius:" + ("7px")  + ";margin-right:" + ("4px")  + ";padding-right:" + ("6px")  + ";}table.wideFormBuilder>tbody>tr>td>div{padding-left:" + ("5px")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem-selected{background-color:" + ("#d1d1d1")  + ";}html>body .gwt-TabBarItem td.topLeft,html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem .tabTopLeft{background-image:" + ("url(/commander/lib/images/cornerwhite_tl.gif)")  + ";background-position:" + ("top"+ " " +"right")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem .tabTopRight{background-image:" + ("url(/commander/lib/images/cornerwhite_tr.gif)")  + ";background-position:" + ("top"+ " " +"left")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem-selected .tabTopRight{background-image:" + ("url(/commander/lib/images/cornergray_d1d1d1_tr.gif)")  + ";background-position:") + (("top"+ " " +"left")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem-selected .tabTopLeft{background-image:" + ("url(/commander/lib/images/cornergray_d1d1d1_tl.gif)")  + ";background-position:" + ("top"+ " " +"right")  + ";}html>body .gwt-TabBarItem .bottomLeft{background-image:" + ("url(/commander/lib/images/cornerwhite_bl.gif)")  + ";}html>body .gwt-TabBarItem .middleLeft,html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem .middleLeft{background:" + ("#fff")  + ";}html>body .gwt-TabBarItem .bottomCenter{background-image:" + ("url(/commander/lib/images/cornerwhite_bm.gif)")  + ";}html>body .gwt-TabBarItem .topCenter,html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem .tabTopCenter{background-image:" + ("url(/commander/lib/images/cornerwhite_tm.gif)")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem-selected .tabTopCenter{background-image:" + ("url(/commander/lib/images/cornergray_d1d1d1_tm.gif)")  + ";}html>body .gwt-TabBarItem-selected td.topLeft{background-image:" + ("url(/commander/lib/images/cornergray_d1d1d1_tl.gif)")  + ";background-position:" + ("top"+ " " +"right")  + ";}html>body .gwt-TabBarItem-selected .bottomLeft{background-image:" + ("url(/commander/lib/images/cornergray_d1d1d1_bl.gif)") ) + (";}html>body .gwt-TabBarItem-selected .middleLeft{background:" + ("#d1d1d1")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem .tabMiddleLeft{background:" + ("#fff")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem .tabMiddleCenter{background:" + ("#fff")  + ";border-bottom:" + ("none")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem .tabMiddleRight{background:" + ("#fff")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem-selected .tabMiddleCenter{border-bottom:" + ("none")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem .tabMiddleLeft{border-right:" + ("1px"+ " " +"solid"+ " " +"#d1d1d1")  + ";border-bottom:" + ("none")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem .tabMiddleRight{border-left:" + ("1px"+ " " +"solid"+ " " +"#d1d1d1")  + ";border-bottom:" + ("none")  + ";}html>body .gwt-DecoratedTabPanel .gwt-TabPanelBottom{border:") + (("2px"+ " " +"solid"+ " " +"#d1d1d1")  + ";-moz-border-radius:" + ("7px")  + ";-webkit-border-radius:" + ("7px")  + ";border-bottom-right-radius:" + ("7px")  + ";border-bottom-left-radius:" + ("7px")  + ";border-top-right-radius:" + ("7px")  + ";border-top-left-radius:" + ("7px")  + ";overflow:" + ("visible")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem-selected .tabMiddleLeft,html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem-selected .tabMiddleCenter,html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem-selected .tabMiddleRight{background:" + ("#d1d1d1")  + ";border-bottom:" + ("none")  + ";}html>body .gwt-TabBarItem-selected .bottomCenter{background-image:" + ("url(/commander/lib/images/cornergray_d1d1d1_bm.gif)") ) + (";}html>body .gwt-TabBarItem-selected .topCenter{background-image:" + ("url(/commander/lib/images/cornergray_d1d1d1_tm.gif)")  + ";}.wideForm{width:" + ("100%")  + ";}table.wideForm input{padding:" + ("2px")  + ";}table.wideForm textarea{padding:" + ("2px")  + ";height:" + ("5em")  + ";}table.wideForm input.wideInput{width:" + ("98%")  + ";}table.wideForm input.mediumInput{width:" + ("250px")  + ";}table.wideForm input.narrowInput{width:" + ("100px")  + ";}table.wideForm textarea{vertical-align:" + ("middle")  + ";width:" + ("98%")  + ";}table.wideForm textarea.mediumInput{vertical-align:") + (("middle")  + ";width:" + ("200px")  + ";}table.pageHeader>tbody>tr>td{padding:" + ("0"+ " " +"0"+ " " +"5px")  + ";color:" + ("#687291")  + ";}div.header_title{color:" + ("#687291")  + ";font-size:" + ("160%")  + ";font-weight:" + ("bold")  + ";}div.header_titleSeparator{font-size:" + ("140%")  + ";font-weight:" + ("normal")  + ";padding:" + ("0"+ " " +"8px")  + ";}div.GKVJYERBLD{font-size:" + ("140%") ) + (";font-weight:" + ("normal")  + ";}div.description_text{width:" + ("500px")  + ";padding-bottom:" + ("10px")  + ";}.propertiesTab .nestedSheet td{padding-left:" + ("3px")  + ";}table.spacedTable td{padding-left:" + ("1px")  + ";padding-right:" + ("1px")  + ";}td.whiteBackground{background:" + ("white")  + ";}.noDisplay{display:" + ("none")  + ";}tr.reportTableHeader td{height:" + ((BundledResources_default_StaticClientBundleGenerator.this.listViewHeader()).getHeight() + "px")  + ";overflow:" + ("hidden")  + ";background:") + (("url(\"" + (BundledResources_default_StaticClientBundleGenerator.this.listViewHeader()).getSafeUri().asString() + "\") -" + (BundledResources_default_StaticClientBundleGenerator.this.listViewHeader()).getLeft() + "px -" + (BundledResources_default_StaticClientBundleGenerator.this.listViewHeader()).getTop() + "px  repeat-x")  + ";height:" + ("inherit")  + ";width:" + ("inherit")  + ";border-left:" + ("1px"+ " " +"solid"+ " " +"#bcbec4")  + ";border-bottom:" + ("2px"+ " " +"solid"+ " " +"#bcbec4")  + ";padding:" + ("0"+ " " +"5px")  + ";}tr.reportTableHeader td label{position:" + ("relative")  + ";bottom:" + ("2px")  + ";right:" + ("2px")  + ";}tr.reportTableRow td{padding-right:" + ("5px")  + ";}.gwt-MenuBarPopup{margin:" + ("0"+ " " +"3px"+ " " +"0"+ " " +"0") ) + (";background-color:" + ("#fff")  + ";}.gwt-MenuBarPopup .menuPopupTopCenter{background-color:" + ("#ddd")  + ";height:" + ("1px")  + ";}.gwt-MenuBarPopup .menuPopupBottomCenter{background-color:" + ("#ddd")  + ";height:" + ("2px")  + ";}.gwt-MenuBarPopup .menuPopupMiddleLeft{background-color:" + ("#ddd")  + ";width:" + ("1px")  + ";}.gwt-MenuBarPopup .menuPopupMiddleRight{background-color:" + ("#ddd")  + ";width:" + ("2px")  + ";}.gwt-MenuBarPopup .menuPopupTopLeftInner{background-color:" + ("#ddd")  + ";width:") + (("1px")  + ";}.gwt-MenuBarPopup .menuPopupTopRightInner{background-color:" + ("#ddd")  + ";width:" + ("2px")  + ";}.gwt-MenuBarPopup .menuPopupBottomLeftInner{background-color:" + ("#ddd")  + ";width:" + ("1px")  + ";}.gwt-MenuBarPopup .menuPopupBottomRightInner{background-color:" + ("#ddd")  + ";width:" + ("2px")  + ";}.gwt-RadioButton{display:" + ("block")  + ";padding:" + ("0")  + ";}td.GKVJYERBCB,div.GKVJYERBCB{color:" + ("#999")  + ";padding:" + ("0"+ " " +"3px"+ " " +"0"+ " " +"0") ) + (";}.GKVJYERBOF td.GKVJYERBDB,.GKVJYERBOF tr.GKVJYERBDB{background:" + ("#dedee4")  + ";border-left:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";border-bottom:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";padding:" + ("3px"+ " " +"10px")  + ";}.GKVJYERBOF td.GKVJYERBGF,.GKVJYERBOF tr.GKVJYERBGF{background:" + ("#f1f1f4")  + ";border-left:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";border-bottom:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";padding:" + ("3px"+ " " +"10px")  + ";}.GKVJYERBOF tr.GKVJYERBGF>td,.GKVJYERBOF tr.GKVJYERBDB>td{padding-right:" + ("5px")  + ";}.GKVJYERBOF .GKVJYERBOD tr.GKVJYERBDB:hover,.GKVJYERBOF .GKVJYERBOD tr.GKVJYERBGF:hover{background:" + ("#a1a1a4")  + ";border-left:") + (("1px"+ " " +"solid"+ " " +"#fff")  + ";border-bottom:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";cursor:" + ("pointer")  + ";padding:" + ("3px"+ " " +"10px")  + ";}.GKVJYERBOF .GKVJYERBOD tr.GKVJYERBC,.GKVJYERBOF .GKVJYERBOD tr.GKVJYERBC:hover{background:" + ("none")  + ";cursor:" + ("default")  + ";}td.GKVJYERBJD{height:" + ((BundledResources_default_StaticClientBundleGenerator.this.listViewHeader()).getHeight() + "px")  + ";overflow:" + ("hidden")  + ";background:" + ("url(\"" + (BundledResources_default_StaticClientBundleGenerator.this.listViewHeader()).getSafeUri().asString() + "\") -" + (BundledResources_default_StaticClientBundleGenerator.this.listViewHeader()).getLeft() + "px -" + (BundledResources_default_StaticClientBundleGenerator.this.listViewHeader()).getTop() + "px  repeat-x")  + ";height:" + ("inherit")  + ";width:" + ("inherit") ) + (";border-left:" + ("1px"+ " " +"solid"+ " " +"#bcbec4")  + ";border-bottom:" + ("2px"+ " " +"solid"+ " " +"#bcbec4")  + ";padding:" + ("3px"+ " " +"10px")  + ";}td.GKVJYERBDB{background:" + ("#dedee4")  + ";border-left:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";border-bottom:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";padding:" + ("3px"+ " " +"10px")  + ";}td.GKVJYERBGF{background:" + ("#f1f1f4")  + ";border-left:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";border-bottom:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";padding:") + (("3px"+ " " +"10px")  + ";}.GKVJYERBGE{border:" + ("1px"+ " " +"solid"+ " " +"#ddd")  + ";}")) : ((".GKVJYERBFF,.GKVJYERBFF a,.GKVJYERBFF a:hover,a.GKVJYERBFF,a.GKVJYERBFF:hover{background:" + ("transparent"+ " " +"none"+ " " +"repeat"+ " " +"scroll"+ " " +"0")  + ";}table.formBuilder .gwt-Label{padding-right:" + ("10px")  + ";}.formBuilder-textAreaLabel,.GKVJYERBOG{padding-top:" + ("11px")  + ";}.formBuilder-textEntryLabel,.GKVJYERBPG{padding-top:" + ("8px")  + ";}.formBuilder-checkboxLabel,.GKVJYERBH{padding-top:" + ("3px")  + ";}.formBuilder-radioLabel,.GKVJYERBJF,.formBuilder-dropdownLabel,.GKVJYERBBB{padding-top:" + ("8px")  + ";}.formBuilder-label,.formBuilder-required{width:" + ("0")  + ";}td.GKVJYERBHB{width:" + ("148px")  + ";}div.GKVJYERBHB{font-size:" + ("110%")  + ";font-weight:" + ("bold")  + ";color:") + (("#687291")  + ";margin-top:" + ("6px")  + ";}tr.GKVJYERBMB{border-bottom-color:" + ("#ccc")  + ";border-bottom-style:" + ("dotted")  + ";border-bottom-width:" + ("1px")  + ";}img.GKVJYERBL{margin-top:" + ("10px")  + ";margin-right:" + ("10px")  + ";vertical-align:" + ("middle")  + ";}.GKVJYERBJ{background-color:" + ("#efefef")  + ";border-color:" + ("#666")  + ";border-width:" + ("1px"+ " " +"3px"+ " " +"3px"+ " " +"1px") ) + (";border-style:" + ("solid"+ " " +"solid"+ " " +"solid"+ " " +"solid")  + ";}.GKVJYERBK{font-weight:" + ("normal")  + ";border:" + ("1px"+ " " +"solid"+ " " +"#efefef")  + ";background-color:" + ("#efefef")  + ";padding:" + ("3px")  + ";}.GKVJYERBK:hover{background-color:" + ("#def")  + ";border:" + ("1px"+ " " +"solid"+ " " +"#c1e0ff")  + ";}.GKVJYERBMF{padding-left:" + ("5px")  + ";padding-top:" + ("10px")  + ";}.formTable-label,.formTable-textValue{padding-right:" + ("5px")  + ";padding-top:") + (("10px")  + ";vertical-align:" + ("top")  + ";width:" + ("150px")  + ";}.formTable-label>*{float:" + ("right")  + ";}.GKVJYERBFC{width:" + ("100%")  + ";color:" + ("#696969")  + ";}.GKVJYERBHC{float:" + ("none")  + ";}.GKVJYERBGC{display:" + ("none")  + ";}.GKVJYERBND{cursor:" + ("help")  + ";border-bottom-color:" + ("#d8d8d8")  + ";border-bottom-width:" + ("thin") ) + (";border-bottom-style:" + ("dashed")  + ";}.formBuilder-requiredLabel,.GKVJYERBNF{padding-left:" + ("5px")  + ";}.GKVJYERBDE .Caption{color:" + ("#687291")  + ";font-weight:" + ("bold")  + ";font-size:" + ("110%")  + ";}.form-value input,.form-value textarea{margin-top:" + ("8px")  + ";padding:" + ("2px")  + ";}.form-value select{margin-top:" + ("8px")  + ";}.form-value input.gwt-TextBox{width:" + ("100%")  + ";}.form-value textarea{width:" + ("100%")  + ";vertical-align:") + (("middle")  + ";}.form-value{padding-right:" + ("1em")  + ";}.GKVJYERBI td.form-value input.gwt-TextBox{width:" + ("17.8em")  + ";}.gwt-SuggestBoxPopup .item{cursor:" + ("default")  + ";padding:" + ("2px"+ " " +"6px")  + ";}.gwt-Anchor{cursor:" + ("pointer")  + ";}.grayDecoratorPanel{margin-bottom:" + ("10px")  + ";}.grayDecoratorPanel .topLeft{background-image:" + ("url(/commander/lib/images/cornergray_tl.gif)")  + ";background-position:" + ("left"+ " " +"top")  + ";background-repeat:" + ("no-repeat")  + ";height:" + ("6px") ) + (";width:" + ("5px")  + ";}.grayDecoratorPanel .topCenter{background-color:" + ("#eeeff2")  + ";background-image:" + ("url(/commander/lib/images/cornergray_tm.gif)")  + ";background-position:" + ("center"+ " " +"top")  + ";background-repeat:" + ("repeat-x")  + ";height:" + ("6px")  + ";width:" + ("auto")  + ";}.grayDecoratorPanel .topRight{background-image:" + ("url(/commander/lib/images/cornergray_tr.gif)")  + ";background-position:" + ("right"+ " " +"top")  + ";background-repeat:" + ("no-repeat")  + ";height:") + (("6px")  + ";width:" + ("5px")  + ";}.grayDecoratorPanel .middleLeft{background-color:" + ("#eeeff2")  + ";border-left:" + ("1px"+ " " +"solid"+ " " +"#d1d1d1")  + ";padding:" + ("0"+ " " +"0"+ " " +"5px"+ " " +"0")  + ";font-size:" + ("100%")  + ";}.grayDecoratorPanel .middleCenter{background-color:" + ("#eeeff2")  + ";padding:" + ("0")  + ";font-size:" + ("100%")  + ";}.grayDecoratorPanel .middleRight{background-color:" + ("#eeeff2")  + ";border-right:" + ("1px"+ " " +"solid"+ " " +"#d1d1d1") ) + (";padding:" + ("0"+ " " +"0"+ " " +"5px"+ " " +"0")  + ";font-size:" + ("100%")  + ";}.grayDecoratorPanel .bottomLeft{background-image:" + ("url(/commander/lib/images/cornergray_bl.gif)")  + ";background-position:" + ("left"+ " " +"bottom")  + ";background-repeat:" + ("no-repeat")  + ";height:" + ("6px")  + ";width:" + ("5px")  + ";}.grayDecoratorPanel .bottomCenter{background-color:" + ("#eeeff2")  + ";background-image:" + ("url(/commander/lib/images/cornergray_bm.gif)")  + ";background-position:" + ("center"+ " " +"bottom")  + ";background-repeat:") + (("repeat-x")  + ";height:" + ("6px")  + ";}.grayDecoratorPanel .bottomRight{background-image:" + ("url(/commander/lib/images/cornergray_br.gif)")  + ";background-position:" + ("right"+ " " +"bottom")  + ";background-repeat:" + ("no-repeat")  + ";height:" + ("6px")  + ";width:" + ("5px")  + ";}.grayDecoratorPanel .borderLeft{border-left:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";}.gwt-PopupPanel input{border:" + ("none")  + ";padding:" + ("0")  + ";}.gwt-PopupPanel input:focus{background-color:" + ("#fff") ) + (";border:" + ("none")  + ";}.gwt-TextBox,input.gwt-SuggestBox,.gwt-PasswordTextBox{width:" + ("200px")  + ";}.inlinediv{display:" + ("inline")  + ";}button.smallButton{margin:" + ("0"+ " " +"0"+ " " +"0"+ " " +"0")  + ";padding:" + ("0"+ " " +"3px")  + ";}div.gwt-SuggestBoxPopup{border:" + ("1px"+ " " +"solid"+ " " +"#d1d1d1")  + ";}div.link:hover{color:" + ("#000")  + ";text-decoration:" + ("none")  + ";margin-right:" + ("0")  + ";padding:" + ("1px")  + ";background-image:") + (("url(/commander/lib/images/dottedBorder1.gif)")  + ";background-position:" + ("bottom")  + ";background-repeat:" + ("repeat-x")  + ";display:" + ("inline")  + ";}.gwt-SuggestBoxPopup .suggestPopupContent{background:" + ("white")  + ";}.gwt-SuggestBoxPopup .item-selected{background:" + ("none"+ " " +"repeat"+ " " +"scroll"+ " " +"0"+ " " +"0"+ " " +"#d1d1d1")  + ";}.gwt-SuggestBoxPopup .suggestPopupTopLeftInner,.gwt-SuggestBoxPopup .suggestPopupTopRightInner,.gwt-SuggestBoxPopup .suggestPopupBottomLeftInner,.gwt-SuggestBoxPopup .suggestPopupBottomRightInner{height:" + ("inherit")  + ";width:" + ("inherit")  + ";}table.wideForm_diagnostic,table.form_diagnostic{color:" + ("#c00")  + ";font-size:" + ("100%")  + ";}table.form_diagnostic img.icon{vertical-align:" + ("top") ) + (";padding-right:" + ("3px")  + ";margin-top:" + ("1px")  + ";}table.form_diagnostic a,table.wideForm_diagnostic a{color:" + ("#c00")  + ";}table.wideForm>tbody>tr>td:first-child{width:" + ("150px")  + ";vertical-align:" + ("top")  + ";text-align:" + ("right")  + ";}table.wideForm>tbody>tr>td[align=\"left\"]:first-child{text-align:" + ("left")  + ";}table.wideForm_diagnostic img.icon{vertical-align:" + ("top")  + ";padding-right:" + ("3px")  + ";margin-top:" + ("1px")  + ";}.dialogTopCenter{border-bottom:") + (("1px"+ " " +"solid"+ " " +"#d1d1d1")  + ";}.gwt-DialogBox .Caption{background:" + ("none")  + ";border:" + ("none")  + ";}.dialogMiddleLeftInner,.dialogMiddleRightInner,html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem .tabMiddleRightInner,html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem .tabMiddleLeftInner{width:" + ("1px")  + ";}.dialogMiddleCenter{background:" + ("none"+ " " +"repeat"+ " " +"scroll"+ " " +"0"+ " " +"0"+ " " +"#fff")  + ";}td.VerticalTabPanelRight{border:" + ("1px"+ " " +"solid"+ " " +"#d1d1d1")  + ";-moz-border-radius:" + ("7px"+ " " +"7px"+ " " +"7px"+ " " +"0")  + ";-webkit-border-radius:" + ("7px")  + ";border-bottom-left-radius:" + ("0")  + ";border-bottom-right-radius:" + ("7px")  + ";border-top-left-radius:" + ("7px") ) + (";border-top-right-radius:" + ("7px")  + ";margin-left:" + ("4px")  + ";padding-left:" + ("6px")  + ";}table.wideFormBuilder>tbody>tr>td>div{padding-right:" + ("5px")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem-selected{background-color:" + ("#d1d1d1")  + ";}html>body .gwt-TabBarItem td.topLeft,html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem .tabTopLeft{background-image:" + ("url(/commander/lib/images/cornerwhite_tl.gif)")  + ";background-position:" + ("top"+ " " +"left")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem .tabTopRight{background-image:" + ("url(/commander/lib/images/cornerwhite_tr.gif)")  + ";background-position:" + ("top"+ " " +"right")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem-selected .tabTopRight{background-image:" + ("url(/commander/lib/images/cornergray_d1d1d1_tr.gif)")  + ";background-position:") + (("top"+ " " +"right")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem-selected .tabTopLeft{background-image:" + ("url(/commander/lib/images/cornergray_d1d1d1_tl.gif)")  + ";background-position:" + ("top"+ " " +"left")  + ";}html>body .gwt-TabBarItem .bottomLeft{background-image:" + ("url(/commander/lib/images/cornerwhite_bl.gif)")  + ";}html>body .gwt-TabBarItem .middleLeft,html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem .middleLeft{background:" + ("#fff")  + ";}html>body .gwt-TabBarItem .bottomCenter{background-image:" + ("url(/commander/lib/images/cornerwhite_bm.gif)")  + ";}html>body .gwt-TabBarItem .topCenter,html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem .tabTopCenter{background-image:" + ("url(/commander/lib/images/cornerwhite_tm.gif)")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem-selected .tabTopCenter{background-image:" + ("url(/commander/lib/images/cornergray_d1d1d1_tm.gif)")  + ";}html>body .gwt-TabBarItem-selected td.topLeft{background-image:" + ("url(/commander/lib/images/cornergray_d1d1d1_tl.gif)")  + ";background-position:" + ("top"+ " " +"left")  + ";}html>body .gwt-TabBarItem-selected .bottomLeft{background-image:" + ("url(/commander/lib/images/cornergray_d1d1d1_bl.gif)") ) + (";}html>body .gwt-TabBarItem-selected .middleLeft{background:" + ("#d1d1d1")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem .tabMiddleLeft{background:" + ("#fff")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem .tabMiddleCenter{background:" + ("#fff")  + ";border-bottom:" + ("none")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem .tabMiddleRight{background:" + ("#fff")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem-selected .tabMiddleCenter{border-bottom:" + ("none")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem .tabMiddleLeft{border-left:" + ("1px"+ " " +"solid"+ " " +"#d1d1d1")  + ";border-bottom:" + ("none")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem .tabMiddleRight{border-right:" + ("1px"+ " " +"solid"+ " " +"#d1d1d1")  + ";border-bottom:" + ("none")  + ";}html>body .gwt-DecoratedTabPanel .gwt-TabPanelBottom{border:") + (("2px"+ " " +"solid"+ " " +"#d1d1d1")  + ";-moz-border-radius:" + ("7px")  + ";-webkit-border-radius:" + ("7px")  + ";border-bottom-left-radius:" + ("7px")  + ";border-bottom-right-radius:" + ("7px")  + ";border-top-left-radius:" + ("7px")  + ";border-top-right-radius:" + ("7px")  + ";overflow:" + ("visible")  + ";}html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem-selected .tabMiddleLeft,html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem-selected .tabMiddleCenter,html>body .gwt-DecoratedTabPanel .gwt-DecoratedTabBar .gwt-TabBarItem-selected .tabMiddleRight{background:" + ("#d1d1d1")  + ";border-bottom:" + ("none")  + ";}html>body .gwt-TabBarItem-selected .bottomCenter{background-image:" + ("url(/commander/lib/images/cornergray_d1d1d1_bm.gif)") ) + (";}html>body .gwt-TabBarItem-selected .topCenter{background-image:" + ("url(/commander/lib/images/cornergray_d1d1d1_tm.gif)")  + ";}.wideForm{width:" + ("100%")  + ";}table.wideForm input{padding:" + ("2px")  + ";}table.wideForm textarea{padding:" + ("2px")  + ";height:" + ("5em")  + ";}table.wideForm input.wideInput{width:" + ("98%")  + ";}table.wideForm input.mediumInput{width:" + ("250px")  + ";}table.wideForm input.narrowInput{width:" + ("100px")  + ";}table.wideForm textarea{vertical-align:" + ("middle")  + ";width:" + ("98%")  + ";}table.wideForm textarea.mediumInput{vertical-align:") + (("middle")  + ";width:" + ("200px")  + ";}table.pageHeader>tbody>tr>td{padding:" + ("0"+ " " +"0"+ " " +"5px")  + ";color:" + ("#687291")  + ";}div.header_title{color:" + ("#687291")  + ";font-size:" + ("160%")  + ";font-weight:" + ("bold")  + ";}div.header_titleSeparator{font-size:" + ("140%")  + ";font-weight:" + ("normal")  + ";padding:" + ("0"+ " " +"8px")  + ";}div.GKVJYERBLD{font-size:" + ("140%") ) + (";font-weight:" + ("normal")  + ";}div.description_text{width:" + ("500px")  + ";padding-bottom:" + ("10px")  + ";}.propertiesTab .nestedSheet td{padding-right:" + ("3px")  + ";}table.spacedTable td{padding-right:" + ("1px")  + ";padding-left:" + ("1px")  + ";}td.whiteBackground{background:" + ("white")  + ";}.noDisplay{display:" + ("none")  + ";}tr.reportTableHeader td{height:" + ((BundledResources_default_StaticClientBundleGenerator.this.listViewHeader()).getHeight() + "px")  + ";overflow:" + ("hidden")  + ";background:") + (("url(\"" + (BundledResources_default_StaticClientBundleGenerator.this.listViewHeader()).getSafeUri().asString() + "\") -" + (BundledResources_default_StaticClientBundleGenerator.this.listViewHeader()).getLeft() + "px -" + (BundledResources_default_StaticClientBundleGenerator.this.listViewHeader()).getTop() + "px  repeat-x")  + ";height:" + ("inherit")  + ";width:" + ("inherit")  + ";border-right:" + ("1px"+ " " +"solid"+ " " +"#bcbec4")  + ";border-bottom:" + ("2px"+ " " +"solid"+ " " +"#bcbec4")  + ";padding:" + ("0"+ " " +"5px")  + ";}tr.reportTableHeader td label{position:" + ("relative")  + ";bottom:" + ("2px")  + ";left:" + ("2px")  + ";}tr.reportTableRow td{padding-left:" + ("5px")  + ";}.gwt-MenuBarPopup{margin:" + ("0"+ " " +"0"+ " " +"0"+ " " +"3px") ) + (";background-color:" + ("#fff")  + ";}.gwt-MenuBarPopup .menuPopupTopCenter{background-color:" + ("#ddd")  + ";height:" + ("1px")  + ";}.gwt-MenuBarPopup .menuPopupBottomCenter{background-color:" + ("#ddd")  + ";height:" + ("2px")  + ";}.gwt-MenuBarPopup .menuPopupMiddleLeft{background-color:" + ("#ddd")  + ";width:" + ("1px")  + ";}.gwt-MenuBarPopup .menuPopupMiddleRight{background-color:" + ("#ddd")  + ";width:" + ("2px")  + ";}.gwt-MenuBarPopup .menuPopupTopLeftInner{background-color:" + ("#ddd")  + ";width:") + (("1px")  + ";}.gwt-MenuBarPopup .menuPopupTopRightInner{background-color:" + ("#ddd")  + ";width:" + ("2px")  + ";}.gwt-MenuBarPopup .menuPopupBottomLeftInner{background-color:" + ("#ddd")  + ";width:" + ("1px")  + ";}.gwt-MenuBarPopup .menuPopupBottomRightInner{background-color:" + ("#ddd")  + ";width:" + ("2px")  + ";}.gwt-RadioButton{display:" + ("block")  + ";padding:" + ("0")  + ";}td.GKVJYERBCB,div.GKVJYERBCB{color:" + ("#999")  + ";padding:" + ("0"+ " " +"0"+ " " +"0"+ " " +"3px") ) + (";}.GKVJYERBOF td.GKVJYERBDB,.GKVJYERBOF tr.GKVJYERBDB{background:" + ("#dedee4")  + ";border-right:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";border-bottom:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";padding:" + ("3px"+ " " +"10px")  + ";}.GKVJYERBOF td.GKVJYERBGF,.GKVJYERBOF tr.GKVJYERBGF{background:" + ("#f1f1f4")  + ";border-right:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";border-bottom:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";padding:" + ("3px"+ " " +"10px")  + ";}.GKVJYERBOF tr.GKVJYERBGF>td,.GKVJYERBOF tr.GKVJYERBDB>td{padding-left:" + ("5px")  + ";}.GKVJYERBOF .GKVJYERBOD tr.GKVJYERBDB:hover,.GKVJYERBOF .GKVJYERBOD tr.GKVJYERBGF:hover{background:" + ("#a1a1a4")  + ";border-right:") + (("1px"+ " " +"solid"+ " " +"#fff")  + ";border-bottom:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";cursor:" + ("pointer")  + ";padding:" + ("3px"+ " " +"10px")  + ";}.GKVJYERBOF .GKVJYERBOD tr.GKVJYERBC,.GKVJYERBOF .GKVJYERBOD tr.GKVJYERBC:hover{background:" + ("none")  + ";cursor:" + ("default")  + ";}td.GKVJYERBJD{height:" + ((BundledResources_default_StaticClientBundleGenerator.this.listViewHeader()).getHeight() + "px")  + ";overflow:" + ("hidden")  + ";background:" + ("url(\"" + (BundledResources_default_StaticClientBundleGenerator.this.listViewHeader()).getSafeUri().asString() + "\") -" + (BundledResources_default_StaticClientBundleGenerator.this.listViewHeader()).getLeft() + "px -" + (BundledResources_default_StaticClientBundleGenerator.this.listViewHeader()).getTop() + "px  repeat-x")  + ";height:" + ("inherit")  + ";width:" + ("inherit") ) + (";border-right:" + ("1px"+ " " +"solid"+ " " +"#bcbec4")  + ";border-bottom:" + ("2px"+ " " +"solid"+ " " +"#bcbec4")  + ";padding:" + ("3px"+ " " +"10px")  + ";}td.GKVJYERBDB{background:" + ("#dedee4")  + ";border-right:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";border-bottom:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";padding:" + ("3px"+ " " +"10px")  + ";}td.GKVJYERBGF{background:" + ("#f1f1f4")  + ";border-right:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";border-bottom:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";padding:") + (("3px"+ " " +"10px")  + ";}.GKVJYERBGE{border:" + ("1px"+ " " +"solid"+ " " +"#ddd")  + ";}"));
      }
      public java.lang.String Caption(){
        return "Caption";
      }
      public java.lang.String VerticalTabPanelRight(){
        return "VerticalTabPanelRight";
      }
      public java.lang.String activeRow(){
        return "GKVJYERBC";
      }
      public java.lang.String borderLeft(){
        return "borderLeft";
      }
      public java.lang.String bottomCenter(){
        return "bottomCenter";
      }
      public java.lang.String bottomLeft(){
        return "bottomLeft";
      }
      public java.lang.String bottomRight(){
        return "bottomRight";
      }
      public java.lang.String checkboxLabel(){
        return "GKVJYERBH";
      }
      public java.lang.String configWidth(){
        return "GKVJYERBI";
      }
      public java.lang.String contextMenuBar(){
        return "GKVJYERBJ";
      }
      public java.lang.String contextMenuItem(){
        return "GKVJYERBK";
      }
      public java.lang.String corner_image(){
        return "GKVJYERBL";
      }
      public java.lang.String description_text(){
        return "description_text";
      }
      public java.lang.String dialogMiddleCenter(){
        return "dialogMiddleCenter";
      }
      public java.lang.String dialogMiddleLeftInner(){
        return "dialogMiddleLeftInner";
      }
      public java.lang.String dialogMiddleRightInner(){
        return "dialogMiddleRightInner";
      }
      public java.lang.String dialogTopCenter(){
        return "dialogTopCenter";
      }
      public java.lang.String dropdownLabel(){
        return "GKVJYERBBB";
      }
      public java.lang.String emptyTable(){
        return "GKVJYERBCB";
      }
      public java.lang.String evenRow(){
        return "GKVJYERBDB";
      }
      public java.lang.String formBuilder(){
        return "formBuilder";
      }
      public java.lang.String formBuilderCheckboxLabel(){
        return "formBuilder-checkboxLabel";
      }
      public java.lang.String formBuilderDropdownLabel(){
        return "formBuilder-dropdownLabel";
      }
      public java.lang.String formBuilderHeader(){
        return "GKVJYERBHB";
      }
      public java.lang.String formBuilderLabel(){
        return "formBuilder-label";
      }
      public java.lang.String formBuilderRadioLabel(){
        return "formBuilder-radioLabel";
      }
      public java.lang.String formBuilderRequired(){
        return "formBuilder-required";
      }
      public java.lang.String formBuilderRequiredLabel(){
        return "formBuilder-requiredLabel";
      }
      public java.lang.String formBuilderSpacer(){
        return "GKVJYERBMB";
      }
      public java.lang.String formBuilderSubheader(){
        return "GKVJYERBNB";
      }
      public java.lang.String formBuilderTextAreaLabel(){
        return "formBuilder-textAreaLabel";
      }
      public java.lang.String formBuilderTextEntryLabel(){
        return "formBuilder-textEntryLabel";
      }
      public java.lang.String formLabel(){
        return "GKVJYERBAC";
      }
      public java.lang.String formTableLabel(){
        return "formTable-label";
      }
      public java.lang.String formTableTextValue(){
        return "formTable-textValue";
      }
      public java.lang.String formValue(){
        return "form-value";
      }
      public java.lang.String form_diagnostic(){
        return "form_diagnostic";
      }
      public java.lang.String form_help(){
        return "GKVJYERBFC";
      }
      public java.lang.String form_help_hidden(){
        return "GKVJYERBGC";
      }
      public java.lang.String form_help_label(){
        return "GKVJYERBHC";
      }
      public java.lang.String form_labelText(){
        return "form_labelText";
      }
      public java.lang.String grayDecoratorPanel(){
        return "grayDecoratorPanel";
      }
      public java.lang.String gwtAnchor(){
        return "gwt-Anchor";
      }
      public java.lang.String gwtDecoratedTabBar(){
        return "gwt-DecoratedTabBar";
      }
      public java.lang.String gwtDecoratedTabPanel(){
        return "gwt-DecoratedTabPanel";
      }
      public java.lang.String gwtDialogBox(){
        return "gwt-DialogBox";
      }
      public java.lang.String gwtLabel(){
        return "gwt-Label";
      }
      public java.lang.String gwtMenuBarPopup(){
        return "gwt-MenuBarPopup";
      }
      public java.lang.String gwtPasswordTextBox(){
        return "gwt-PasswordTextBox";
      }
      public java.lang.String gwtPopupPanel(){
        return "gwt-PopupPanel";
      }
      public java.lang.String gwtRadioButton(){
        return "gwt-RadioButton";
      }
      public java.lang.String gwtSuggestBox(){
        return "gwt-SuggestBox";
      }
      public java.lang.String gwtSuggestBoxPopup(){
        return "gwt-SuggestBoxPopup";
      }
      public java.lang.String gwtTabBarItem(){
        return "gwt-TabBarItem";
      }
      public java.lang.String gwtTabBarItemSelected(){
        return "gwt-TabBarItem-selected";
      }
      public java.lang.String gwtTabPanelBottom(){
        return "gwt-TabPanelBottom";
      }
      public java.lang.String gwtTextBox(){
        return "gwt-TextBox";
      }
      public java.lang.String headerRow(){
        return "GKVJYERBJD";
      }
      public java.lang.String header_title(){
        return "header_title";
      }
      public java.lang.String header_title2(){
        return "GKVJYERBLD";
      }
      public java.lang.String header_titleSeparator(){
        return "header_titleSeparator";
      }
      public java.lang.String helpCursor(){
        return "GKVJYERBND";
      }
      public java.lang.String hoverable(){
        return "GKVJYERBOD";
      }
      public java.lang.String icon(){
        return "icon";
      }
      public java.lang.String inlinediv(){
        return "inlinediv";
      }
      public java.lang.String item(){
        return "item";
      }
      public java.lang.String itemSelected(){
        return "item-selected";
      }
      public java.lang.String legendStyle(){
        return "GKVJYERBDE";
      }
      public java.lang.String link(){
        return "link";
      }
      public java.lang.String mediumInput(){
        return "mediumInput";
      }
      public java.lang.String menuBorder(){
        return "GKVJYERBGE";
      }
      public java.lang.String menuPopupBottomCenter(){
        return "menuPopupBottomCenter";
      }
      public java.lang.String menuPopupBottomLeftInner(){
        return "menuPopupBottomLeftInner";
      }
      public java.lang.String menuPopupBottomRightInner(){
        return "menuPopupBottomRightInner";
      }
      public java.lang.String menuPopupMiddleLeft(){
        return "menuPopupMiddleLeft";
      }
      public java.lang.String menuPopupMiddleRight(){
        return "menuPopupMiddleRight";
      }
      public java.lang.String menuPopupTopCenter(){
        return "menuPopupTopCenter";
      }
      public java.lang.String menuPopupTopLeftInner(){
        return "menuPopupTopLeftInner";
      }
      public java.lang.String menuPopupTopRightInner(){
        return "menuPopupTopRightInner";
      }
      public java.lang.String middleCenter(){
        return "middleCenter";
      }
      public java.lang.String middleLeft(){
        return "middleLeft";
      }
      public java.lang.String middleRight(){
        return "middleRight";
      }
      public java.lang.String narrowInput(){
        return "narrowInput";
      }
      public java.lang.String nestedSheet(){
        return "nestedSheet";
      }
      public java.lang.String noDisplay(){
        return "noDisplay";
      }
      public java.lang.String no_underline(){
        return "GKVJYERBFF";
      }
      public java.lang.String oddRow(){
        return "GKVJYERBGF";
      }
      public java.lang.String pageHeader(){
        return "pageHeader";
      }
      public java.lang.String propertiesTab(){
        return "propertiesTab";
      }
      public java.lang.String radioLabel(){
        return "GKVJYERBJF";
      }
      public java.lang.String reportTableHeader(){
        return "reportTableHeader";
      }
      public java.lang.String reportTableRow(){
        return "reportTableRow";
      }
      public java.lang.String requiredLabel(){
        return "GKVJYERBMF";
      }
      public java.lang.String requiredLabelStyle(){
        return "GKVJYERBNF";
      }
      public java.lang.String rowShade(){
        return "GKVJYERBOF";
      }
      public java.lang.String smallButton(){
        return "smallButton";
      }
      public java.lang.String spacedTable(){
        return "spacedTable";
      }
      public java.lang.String suggestPopupBottomLeftInner(){
        return "suggestPopupBottomLeftInner";
      }
      public java.lang.String suggestPopupBottomRightInner(){
        return "suggestPopupBottomRightInner";
      }
      public java.lang.String suggestPopupContent(){
        return "suggestPopupContent";
      }
      public java.lang.String suggestPopupTopLeftInner(){
        return "suggestPopupTopLeftInner";
      }
      public java.lang.String suggestPopupTopRightInner(){
        return "suggestPopupTopRightInner";
      }
      public java.lang.String tabMiddleCenter(){
        return "tabMiddleCenter";
      }
      public java.lang.String tabMiddleLeft(){
        return "tabMiddleLeft";
      }
      public java.lang.String tabMiddleLeftInner(){
        return "tabMiddleLeftInner";
      }
      public java.lang.String tabMiddleRight(){
        return "tabMiddleRight";
      }
      public java.lang.String tabMiddleRightInner(){
        return "tabMiddleRightInner";
      }
      public java.lang.String tabTopCenter(){
        return "tabTopCenter";
      }
      public java.lang.String tabTopLeft(){
        return "tabTopLeft";
      }
      public java.lang.String tabTopRight(){
        return "tabTopRight";
      }
      public java.lang.String textAreaLabel(){
        return "GKVJYERBOG";
      }
      public java.lang.String textEntryLabel(){
        return "GKVJYERBPG";
      }
      public java.lang.String topCenter(){
        return "topCenter";
      }
      public java.lang.String topLeft(){
        return "topLeft";
      }
      public java.lang.String topRight(){
        return "topRight";
      }
      public java.lang.String whiteBackground(){
        return "whiteBackground";
      }
      public java.lang.String wideForm(){
        return "wideForm";
      }
      public java.lang.String wideFormBuilder(){
        return "wideFormBuilder";
      }
      public java.lang.String wideForm_diagnostic(){
        return "wideForm_diagnostic";
      }
      public java.lang.String wideInput(){
        return "wideInput";
      }
    }
    ;
  }
  private static class cssInitializer {
    static {
      _instance0.cssInitializer();
    }
    static com.electriccloud.commander.gwt.client.ui.BundledStyles get() {
      return css;
    }
  }
  public com.electriccloud.commander.gwt.client.ui.BundledStyles css() {
    return cssInitializer.get();
  }
  private void simplePagerStyleInitializer() {
    simplePagerStyle = new com.google.gwt.user.cellview.client.SimplePager.Style() {
      private boolean injected;
      public boolean ensureInjected() {
        if (!injected) {
          injected = true;
          com.google.gwt.dom.client.StyleInjector.inject(getText());
          return true;
        }
        return false;
      }
      public String getName() {
        return "simplePagerStyle";
      }
      public String getText() {
        return (".GKVJYERBDP{padding:" + ("4px"+ " " +"8px")  + ";text-align:" + ("center")  + ";}.GKVJYERBBP{padding:" + ("4px")  + ";cursor:" + ("pointer")  + ";cursor:" + ("hand")  + ";}.GKVJYERBCP{cursor:" + ("default")  + ";}");
      }
      public java.lang.String button(){
        return "GKVJYERBBP";
      }
      public java.lang.String disabledButton(){
        return "GKVJYERBCP";
      }
      public java.lang.String pageDetails(){
        return "GKVJYERBDP";
      }
    }
    ;
  }
  private static class simplePagerStyleInitializer {
    static {
      _instance0.simplePagerStyleInitializer();
    }
    static com.google.gwt.user.cellview.client.SimplePager.Style get() {
      return simplePagerStyle;
    }
  }
  public com.google.gwt.user.cellview.client.SimplePager.Style simplePagerStyle() {
    return simplePagerStyleInitializer.get();
  }
  private void helpIconSmallInitializer() {
    helpIconSmall = new com.google.gwt.resources.client.impl.ImageResourcePrototype(
      "helpIconSmall",
      com.google.gwt.safehtml.shared.UriUtils.fromTrustedString(com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ?bundledImage_None_rtl : bundledImage_None),
      190, 0, 16, 16, false, false
    );
  }
  private static class helpIconSmallInitializer {
    static {
      _instance0.helpIconSmallInitializer();
    }
    static com.google.gwt.resources.client.ImageResource get() {
      return helpIconSmall;
    }
  }
  public com.google.gwt.resources.client.ImageResource helpIconSmall() {
    return helpIconSmallInitializer.get();
  }
  private void listViewHeaderInitializer() {
    listViewHeader = new com.google.gwt.resources.client.impl.ImageResourcePrototype(
      "listViewHeader",
      com.google.gwt.safehtml.shared.UriUtils.fromTrustedString(bundledImage_Horizontal),
      0, 0, 1, 100, false, false
    );
  }
  private static class listViewHeaderInitializer {
    static {
      _instance0.listViewHeaderInitializer();
    }
    static com.google.gwt.resources.client.ImageResource get() {
      return listViewHeader;
    }
  }
  public com.google.gwt.resources.client.ImageResource listViewHeader() {
    return listViewHeaderInitializer.get();
  }
  private void loadingImageLargeInitializer() {
    loadingImageLarge = new com.google.gwt.resources.client.impl.ImageResourcePrototype(
      "loadingImageLarge",
      com.google.gwt.safehtml.shared.UriUtils.fromTrustedString(externalImage),
      0, 0, 32, 32, true, false
    );
  }
  private static class loadingImageLargeInitializer {
    static {
      _instance0.loadingImageLargeInitializer();
    }
    static com.google.gwt.resources.client.ImageResource get() {
      return loadingImageLarge;
    }
  }
  public com.google.gwt.resources.client.ImageResource loadingImageLarge() {
    return loadingImageLargeInitializer.get();
  }
  private void simplePagerFastForwardInitializer() {
    simplePagerFastForward = new com.google.gwt.resources.client.impl.ImageResourcePrototype(
      "simplePagerFastForward",
      com.google.gwt.safehtml.shared.UriUtils.fromTrustedString(com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ?bundledImage_None_rtl : bundledImage_None),
      171, 0, 19, 19, false, false
    );
  }
  private static class simplePagerFastForwardInitializer {
    static {
      _instance0.simplePagerFastForwardInitializer();
    }
    static com.google.gwt.resources.client.ImageResource get() {
      return simplePagerFastForward;
    }
  }
  public com.google.gwt.resources.client.ImageResource simplePagerFastForward() {
    return simplePagerFastForwardInitializer.get();
  }
  private void simplePagerFastForwardDisabledInitializer() {
    simplePagerFastForwardDisabled = new com.google.gwt.resources.client.impl.ImageResourcePrototype(
      "simplePagerFastForwardDisabled",
      com.google.gwt.safehtml.shared.UriUtils.fromTrustedString(com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ?bundledImage_None_rtl : bundledImage_None),
      152, 0, 19, 19, false, false
    );
  }
  private static class simplePagerFastForwardDisabledInitializer {
    static {
      _instance0.simplePagerFastForwardDisabledInitializer();
    }
    static com.google.gwt.resources.client.ImageResource get() {
      return simplePagerFastForwardDisabled;
    }
  }
  public com.google.gwt.resources.client.ImageResource simplePagerFastForwardDisabled() {
    return simplePagerFastForwardDisabledInitializer.get();
  }
  private void simplePagerFirstPageInitializer() {
    simplePagerFirstPage = new com.google.gwt.resources.client.impl.ImageResourcePrototype(
      "simplePagerFirstPage",
      com.google.gwt.safehtml.shared.UriUtils.fromTrustedString(com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ?bundledImage_None_rtl : bundledImage_None),
      133, 0, 19, 19, false, false
    );
  }
  private static class simplePagerFirstPageInitializer {
    static {
      _instance0.simplePagerFirstPageInitializer();
    }
    static com.google.gwt.resources.client.ImageResource get() {
      return simplePagerFirstPage;
    }
  }
  public com.google.gwt.resources.client.ImageResource simplePagerFirstPage() {
    return simplePagerFirstPageInitializer.get();
  }
  private void simplePagerFirstPageDisabledInitializer() {
    simplePagerFirstPageDisabled = new com.google.gwt.resources.client.impl.ImageResourcePrototype(
      "simplePagerFirstPageDisabled",
      com.google.gwt.safehtml.shared.UriUtils.fromTrustedString(com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ?bundledImage_None_rtl : bundledImage_None),
      114, 0, 19, 19, false, false
    );
  }
  private static class simplePagerFirstPageDisabledInitializer {
    static {
      _instance0.simplePagerFirstPageDisabledInitializer();
    }
    static com.google.gwt.resources.client.ImageResource get() {
      return simplePagerFirstPageDisabled;
    }
  }
  public com.google.gwt.resources.client.ImageResource simplePagerFirstPageDisabled() {
    return simplePagerFirstPageDisabledInitializer.get();
  }
  private void simplePagerLastPageInitializer() {
    simplePagerLastPage = new com.google.gwt.resources.client.impl.ImageResourcePrototype(
      "simplePagerLastPage",
      com.google.gwt.safehtml.shared.UriUtils.fromTrustedString(com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ?bundledImage_None_rtl : bundledImage_None),
      95, 0, 19, 19, false, false
    );
  }
  private static class simplePagerLastPageInitializer {
    static {
      _instance0.simplePagerLastPageInitializer();
    }
    static com.google.gwt.resources.client.ImageResource get() {
      return simplePagerLastPage;
    }
  }
  public com.google.gwt.resources.client.ImageResource simplePagerLastPage() {
    return simplePagerLastPageInitializer.get();
  }
  private void simplePagerLastPageDisabledInitializer() {
    simplePagerLastPageDisabled = new com.google.gwt.resources.client.impl.ImageResourcePrototype(
      "simplePagerLastPageDisabled",
      com.google.gwt.safehtml.shared.UriUtils.fromTrustedString(com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ?bundledImage_None_rtl : bundledImage_None),
      76, 0, 19, 19, false, false
    );
  }
  private static class simplePagerLastPageDisabledInitializer {
    static {
      _instance0.simplePagerLastPageDisabledInitializer();
    }
    static com.google.gwt.resources.client.ImageResource get() {
      return simplePagerLastPageDisabled;
    }
  }
  public com.google.gwt.resources.client.ImageResource simplePagerLastPageDisabled() {
    return simplePagerLastPageDisabledInitializer.get();
  }
  private void simplePagerNextPageInitializer() {
    simplePagerNextPage = new com.google.gwt.resources.client.impl.ImageResourcePrototype(
      "simplePagerNextPage",
      com.google.gwt.safehtml.shared.UriUtils.fromTrustedString(com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ?bundledImage_None_rtl : bundledImage_None),
      57, 0, 19, 19, false, false
    );
  }
  private static class simplePagerNextPageInitializer {
    static {
      _instance0.simplePagerNextPageInitializer();
    }
    static com.google.gwt.resources.client.ImageResource get() {
      return simplePagerNextPage;
    }
  }
  public com.google.gwt.resources.client.ImageResource simplePagerNextPage() {
    return simplePagerNextPageInitializer.get();
  }
  private void simplePagerNextPageDisabledInitializer() {
    simplePagerNextPageDisabled = new com.google.gwt.resources.client.impl.ImageResourcePrototype(
      "simplePagerNextPageDisabled",
      com.google.gwt.safehtml.shared.UriUtils.fromTrustedString(com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ?bundledImage_None_rtl : bundledImage_None),
      38, 0, 19, 19, false, false
    );
  }
  private static class simplePagerNextPageDisabledInitializer {
    static {
      _instance0.simplePagerNextPageDisabledInitializer();
    }
    static com.google.gwt.resources.client.ImageResource get() {
      return simplePagerNextPageDisabled;
    }
  }
  public com.google.gwt.resources.client.ImageResource simplePagerNextPageDisabled() {
    return simplePagerNextPageDisabledInitializer.get();
  }
  private void simplePagerPreviousPageInitializer() {
    simplePagerPreviousPage = new com.google.gwt.resources.client.impl.ImageResourcePrototype(
      "simplePagerPreviousPage",
      com.google.gwt.safehtml.shared.UriUtils.fromTrustedString(com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ?bundledImage_None_rtl : bundledImage_None),
      19, 0, 19, 19, false, false
    );
  }
  private static class simplePagerPreviousPageInitializer {
    static {
      _instance0.simplePagerPreviousPageInitializer();
    }
    static com.google.gwt.resources.client.ImageResource get() {
      return simplePagerPreviousPage;
    }
  }
  public com.google.gwt.resources.client.ImageResource simplePagerPreviousPage() {
    return simplePagerPreviousPageInitializer.get();
  }
  private void simplePagerPreviousPageDisabledInitializer() {
    simplePagerPreviousPageDisabled = new com.google.gwt.resources.client.impl.ImageResourcePrototype(
      "simplePagerPreviousPageDisabled",
      com.google.gwt.safehtml.shared.UriUtils.fromTrustedString(com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ?bundledImage_None_rtl : bundledImage_None),
      0, 0, 19, 19, false, false
    );
  }
  private static class simplePagerPreviousPageDisabledInitializer {
    static {
      _instance0.simplePagerPreviousPageDisabledInitializer();
    }
    static com.google.gwt.resources.client.ImageResource get() {
      return simplePagerPreviousPageDisabled;
    }
  }
  public com.google.gwt.resources.client.ImageResource simplePagerPreviousPageDisabled() {
    return simplePagerPreviousPageDisabledInitializer.get();
  }
  private static java.util.HashMap<java.lang.String, com.google.gwt.resources.client.ResourcePrototype> resourceMap;
  private static com.electriccloud.commander.gwt.client.ui.BundledStyles css;
  private static com.google.gwt.user.cellview.client.SimplePager.Style simplePagerStyle;
  private static final java.lang.String bundledImage_None = GWT.getModuleBaseForStaticFiles() + "162BCC62DDA9E1856CAA34C07B01556F.cache.png";
  private static final java.lang.String bundledImage_None_rtl = GWT.getModuleBaseForStaticFiles() + "D3994686840C72FA3A0FF48A343B114E.cache.png";
  private static final java.lang.String bundledImage_Horizontal = GWT.getModuleBaseForStaticFiles() + "2FFB6FEC063E9A714142BB076F48BAAB.cache.png";
  private static final java.lang.String externalImage = GWT.getModuleBaseForStaticFiles() + "37877AD689ED1BF2B48D9FD71D3C4932.cache.gif";
  private static com.google.gwt.resources.client.ImageResource helpIconSmall;
  private static com.google.gwt.resources.client.ImageResource listViewHeader;
  private static com.google.gwt.resources.client.ImageResource loadingImageLarge;
  private static com.google.gwt.resources.client.ImageResource simplePagerFastForward;
  private static com.google.gwt.resources.client.ImageResource simplePagerFastForwardDisabled;
  private static com.google.gwt.resources.client.ImageResource simplePagerFirstPage;
  private static com.google.gwt.resources.client.ImageResource simplePagerFirstPageDisabled;
  private static com.google.gwt.resources.client.ImageResource simplePagerLastPage;
  private static com.google.gwt.resources.client.ImageResource simplePagerLastPageDisabled;
  private static com.google.gwt.resources.client.ImageResource simplePagerNextPage;
  private static com.google.gwt.resources.client.ImageResource simplePagerNextPageDisabled;
  private static com.google.gwt.resources.client.ImageResource simplePagerPreviousPage;
  private static com.google.gwt.resources.client.ImageResource simplePagerPreviousPageDisabled;
  
  public ResourcePrototype[] getResources() {
    return new ResourcePrototype[] {
      css(), 
      simplePagerStyle(), 
      helpIconSmall(), 
      listViewHeader(), 
      loadingImageLarge(), 
      simplePagerFastForward(), 
      simplePagerFastForwardDisabled(), 
      simplePagerFirstPage(), 
      simplePagerFirstPageDisabled(), 
      simplePagerLastPage(), 
      simplePagerLastPageDisabled(), 
      simplePagerNextPage(), 
      simplePagerNextPageDisabled(), 
      simplePagerPreviousPage(), 
      simplePagerPreviousPageDisabled(), 
    };
  }
  public ResourcePrototype getResource(String name) {
    if (GWT.isScript()) {
      return getResourceNative(name);
    } else {
      if (resourceMap == null) {
        resourceMap = new java.util.HashMap<java.lang.String, com.google.gwt.resources.client.ResourcePrototype>();
        resourceMap.put("css", css());
        resourceMap.put("simplePagerStyle", simplePagerStyle());
        resourceMap.put("helpIconSmall", helpIconSmall());
        resourceMap.put("listViewHeader", listViewHeader());
        resourceMap.put("loadingImageLarge", loadingImageLarge());
        resourceMap.put("simplePagerFastForward", simplePagerFastForward());
        resourceMap.put("simplePagerFastForwardDisabled", simplePagerFastForwardDisabled());
        resourceMap.put("simplePagerFirstPage", simplePagerFirstPage());
        resourceMap.put("simplePagerFirstPageDisabled", simplePagerFirstPageDisabled());
        resourceMap.put("simplePagerLastPage", simplePagerLastPage());
        resourceMap.put("simplePagerLastPageDisabled", simplePagerLastPageDisabled());
        resourceMap.put("simplePagerNextPage", simplePagerNextPage());
        resourceMap.put("simplePagerNextPageDisabled", simplePagerNextPageDisabled());
        resourceMap.put("simplePagerPreviousPage", simplePagerPreviousPage());
        resourceMap.put("simplePagerPreviousPageDisabled", simplePagerPreviousPageDisabled());
      }
      return resourceMap.get(name);
    }
  }
  private native ResourcePrototype getResourceNative(String name) /*-{
    switch (name) {
      case 'css': return this.@com.electriccloud.commander.gwt.client.ui.BundledResources::css()();
      case 'simplePagerStyle': return this.@com.google.gwt.user.cellview.client.SimplePager.Resources::simplePagerStyle()();
      case 'helpIconSmall': return this.@com.electriccloud.commander.gwt.client.ui.BundledResources::helpIconSmall()();
      case 'listViewHeader': return this.@com.electriccloud.commander.gwt.client.ui.BundledResources::listViewHeader()();
      case 'loadingImageLarge': return this.@com.electriccloud.commander.gwt.client.ui.BundledResources::loadingImageLarge()();
      case 'simplePagerFastForward': return this.@com.google.gwt.user.cellview.client.SimplePager.Resources::simplePagerFastForward()();
      case 'simplePagerFastForwardDisabled': return this.@com.google.gwt.user.cellview.client.SimplePager.Resources::simplePagerFastForwardDisabled()();
      case 'simplePagerFirstPage': return this.@com.google.gwt.user.cellview.client.SimplePager.Resources::simplePagerFirstPage()();
      case 'simplePagerFirstPageDisabled': return this.@com.google.gwt.user.cellview.client.SimplePager.Resources::simplePagerFirstPageDisabled()();
      case 'simplePagerLastPage': return this.@com.google.gwt.user.cellview.client.SimplePager.Resources::simplePagerLastPage()();
      case 'simplePagerLastPageDisabled': return this.@com.google.gwt.user.cellview.client.SimplePager.Resources::simplePagerLastPageDisabled()();
      case 'simplePagerNextPage': return this.@com.google.gwt.user.cellview.client.SimplePager.Resources::simplePagerNextPage()();
      case 'simplePagerNextPageDisabled': return this.@com.google.gwt.user.cellview.client.SimplePager.Resources::simplePagerNextPageDisabled()();
      case 'simplePagerPreviousPage': return this.@com.google.gwt.user.cellview.client.SimplePager.Resources::simplePagerPreviousPage()();
      case 'simplePagerPreviousPageDisabled': return this.@com.google.gwt.user.cellview.client.SimplePager.Resources::simplePagerPreviousPageDisabled()();
    }
    return null;
  }-*/;
}
