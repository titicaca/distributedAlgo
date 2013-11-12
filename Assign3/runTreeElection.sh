teachnetpath=$(head -1 teachnet.path)
java -jar $teachnetpath --cp . --config tree_election_config.txt --compile

#javac -cp ../teachnet.jar MyAlgorithm.java
#java -cp .:../teachnet.jar teachnet/view/TeachnetFrame

# Windows Users use 
# java -cp .;..\teachnet.jar teachnet/view/TeachnetFrame
