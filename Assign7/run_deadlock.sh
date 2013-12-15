teachnetpath=$(head -1 teachnet.path)
java -jar $teachnetpath --cp . --config deadlock_config.txt --compile

