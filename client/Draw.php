<?php
header("Content-type: image/png");
$image=imagecreatetruecolor(500,400);
$my_colorA=imagecolorallocate($image,51,51,255); 
$my_colorB=imagecolorallocate($image,100,150,215); 
$my_colorC=imagecolorallocate($image,20,20,151); 
$my_colorX=imagecolorallocate($image,216,216,255);
$gray = imagecolorallocate($image, 0xC0, 0xC0, 0xC0);
$navy = imagecolorallocate($image, 0x00, 0x00, 0x80);
$red = imagecolorallocate($image, 0xFF, 0x00, 0x00);
$ptsize=24;$x=20;$y=50;$angle=-10;
imagefill($image,0,0,$my_colorX); 
imagefilledrectangle($image,150,100,250,200,$my_colorB);
imagestring($image,10,10,10,"CSC 443",$my_colorA); imageline($image,10,10,150,150,$my_colorC); 
imagefilledarc($image,200,150, 100, 50,  0, 45,  $navy, IMG_ARC_PIE); 
imagefilledarc($image,200,150, 100, 50, 45, 75,  $gray, IMG_ARC_PIE);
imagefilledarc($image,200,150, 100, 50, 75, 360, $red , IMG_ARC_PIE);
imagedestroy($image); 
?> 
