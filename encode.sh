ffmpeg -r 60 -f image2 -s 1280x720 -i $1.jpg -vcodec libx264 -b 4M -vpre normal -acodec copy vids/output_$2.mp4
.mp4 
