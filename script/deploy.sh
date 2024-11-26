program=eneco-selenium
path=eneco-selenium
local_source_path=/Users/martinschijf/Prive/Sources/Kotlin/
local_target_path=/Users/martinschijf/Prive/runeneco/

cd $local_source_path/$path

rm $local_target_path/$program*.jar
cp target/$program*.jar $local_target_path

cd $local_target_path
#sh ./start.sh


