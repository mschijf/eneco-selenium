program=eneco-selenium
path=eneco-selenium
local_source_path=/Users/martinschijf/Prive/Sources/Kotlin/

cd $local_source_path/$path
ssh pi rm $path/$program*.jar
scp target/$program*.jar pi:~/$path
ssh pi bash $path/script/start.sh




