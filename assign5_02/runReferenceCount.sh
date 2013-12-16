teachnetpath=$(head -1 teachnet.path)
java -jar $teachnetpath --cp . --config reference_count_config.txt --compile

#javac -cp ../teachnet.jar MyAlgorithm.java
#java -cp .:../teachnet.jar teachnet/view/TeachnetFrame

# Windows Users use 
# java -cp .;..\teachnet.jar teachnet/view/TeachnetFrame
