# Copyright 2015 Electric Cloud, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

use ElectricCommander;

push (@::gMatchers,
  {
        id =>          "createBucket",
        pattern =>     q{^Bucket\s(.+)\screated\ssuccessfully},
        action =>           q{
         
                              my $desc = ((defined $::gProperties{"summary"}) ? $::gProperties{"summary"} : '');

                              $desc .= "Bucket $1 created successfully.";
                              
                              setProperty("summary", $desc . "\n");
                             },
  },
  {
        id =>          "listBucket",
        pattern =>     q{^Listed\s(.+)\sbuckets},
        action =>           q{
         
                              my $desc = ((defined $::gProperties{"summary"}) ? $::gProperties{"summary"} : '');

                              $desc .= "Listed $1 buckets.";
                              
                              setProperty("summary", $desc . "\n");
                             },
  },
  {
        id =>          "listFolder",
        pattern =>     q{^Listed\s(.+)\sobjects},
        action =>           q{
         
                              my $desc = ((defined $::gProperties{"summary"}) ? $::gProperties{"summary"} : '');

                              $desc .= "Listed $1 objects.";
                              
                              setProperty("summary", $desc . "\n");
                             },
  },
    {
        id =>          "createFolder",
        pattern =>     q{^Folder\s(.+)\screated\ssuccessfully},
        action =>           q{
         
                              my $desc = ((defined $::gProperties{"summary"}) ? $::gProperties{"summary"} : '');

                              $desc .= "Folder $1 created successfully.";
                                
                              setProperty("summary", $desc . "\n");
                             },
  },
      {
        id =>          "deleteObject",
        pattern =>     q{^Object\s(.+)\sdeleted\ssuccessfully},
        action =>           q{
         
                              my $desc = ((defined $::gProperties{"summary"}) ? $::gProperties{"summary"} : '');

                              $desc .= "Object $1 deleted successfully.";
                              
                              setProperty("summary", $desc . "\n");
                             },
  },
        {
            id =>          "deleteBucketContents",
            pattern =>     q{^Successfully\sdeleted\s(.+)\sitems},
            action =>           q{

                                  my $desc = ((defined $::gProperties{"summary"}) ? $::gProperties{"summary"} : '');

                                  $desc .= "Successfully deleted $1 items.";

                                  setProperty("summary", $desc . "\n");
                                 },
      },
          {
        id =>          "uploadObject",
        pattern =>     q{^Uploaded\s(.+)\ssuccessfully},
        action =>           q{

                              my $desc = ((defined $::gProperties{"summary"}) ? $::gProperties{"summary"} : '');

                              $desc .= "Uploaded $1 successfully.";

                              setProperty("summary", $desc . "\n");
                             },
  },
            {
        id =>          "uploadFolder",
        pattern =>     q{^Uploaded\s(.+)\ssuccessfully},
        action =>           q{

                              my $desc = ((defined $::gProperties{"summary"}) ? $::gProperties{"summary"} : '');

                              $desc .= "Uploaded $1 successfully.";

                              setProperty("summary", $desc . "\n");
                             },
  },
            {
          id =>          "downloadObject",
          pattern =>     q{^Downloaded\s(.+)\ssuccessfully},
          action =>           q{

                                my $desc = ((defined $::gProperties{"summary"}) ? $::gProperties{"summary"} : '');

                                $desc .= "Downloaded $1 successfully.";

                                setProperty("summary", $desc . "\n");
                               },
    },
    {
          id =>          "downloadFolder",
          pattern =>     q{^Downloaded\s(.+)\ssuccessfully},
          action =>           q{

                                my $desc = ((defined $::gProperties{"summary"}) ? $::gProperties{"summary"} : '');

                                $desc .= "Downloaded $1 successfully.";

                                setProperty("summary", $desc . "\n");
                               },
    },
    {
          id =>          "websiteHosting",
          pattern =>     q{^Website\son\s(.+)\schanged\ssuccessfully},
          action =>           q{

                                my $desc = ((defined $::gProperties{"summary"}) ? $::gProperties{"summary"} : '');

                                $desc .= "Website on $1 changed successfully.";

                                setProperty("summary", $desc . "\n");
                               },
    },
    {
              id      => "error2",
              pattern => q{ERROR\s:|[Ee]rror\s:|[Ee]xception},
              action  => q{
                     my $description = ((defined $::gProperties{"summary"}) ?
                              $::gProperties{"summary"} : '');

                        $description .= "$1";

                        setProperty("summary", $description . "\n");
                        incValue("errors"); diagnostic("", "error", -1);

                          }
      }
);
