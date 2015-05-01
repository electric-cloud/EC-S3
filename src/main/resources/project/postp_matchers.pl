use ElectricCommander;

push (@::gMatchers,
  {
        id =>          "createBucket",
        pattern =>     q{^Bucket\s(.+)\screated\ssuccessfully},
        action =>           q{
         
                              my $desc = ((defined $::gProperties{"summary"}) ? $::gProperties{"summary"} : '');

                              $desc .= "Bucket $1 created successfully";
                              
                              setProperty("summary", $desc . "\n");
                             },
  },
  {
        id =>          "listBucket",
        pattern =>     q{^Listed\s(.+)\sbuckets},
        action =>           q{
         
                              my $desc = ((defined $::gProperties{"summary"}) ? $::gProperties{"summary"} : '');

                              $desc .= "Listed $1 buckets";
                              
                              setProperty("summary", $desc . "\n");
                             },
  },
    {
        id =>          "createFolder",
        pattern =>     q{^Folder\s(.+)\screated\ssuccessfully},
        action =>           q{
         
                              my $desc = ((defined $::gProperties{"summary"}) ? $::gProperties{"summary"} : '');

                              $desc .= "Folder $1 created successfully";
                              
                              setProperty("summary", $desc . "\n");
                             },
  },
      {
                 id =>          "error",
                 pattern =>     q{ERROR\s:|[Ee]rror\s:},
                 action =>      q{
                                    incValue("errors"); diagnostic("", "error", -1);
                                    my $desc = ((defined $::gProperties{"summary"}) ? $::gProperties{"summary"} : '');

                                    $desc .= "$1";
                                    
                                    setProperty("summary", $desc . "\n");
                                 },
    }
);
