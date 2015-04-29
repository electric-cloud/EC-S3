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
                 id =>          "error",
                 pattern =>     q{ERROR\s:|[Ee]rror\s:},
                 action =>      q{
                                    incValue("errors"); diagnostic("", "error", -1);

                                 },
    }
);
