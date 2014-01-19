teachnetpath=$(head -1 teachnet.path)
java -jar $teachnetpath --cp . --config ssspan_tree_child_failed.txt --compile
