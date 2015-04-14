# Data that drives the create step picker registration for this plugin.
my %CreateBucket = ( 
  label       => "EC-S3 - CreateBucket", 
  procedure   => "CreateBucket", 
  description => "", 
  category    => "Resource Management" 
);

@::createStepPickerSteps = (\%CreateBucket);
