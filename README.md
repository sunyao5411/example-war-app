# example-war-app


## MySQL Configuration

	# Priority 0
	# Visit http://localhost:8080/app/configuration.html and configure jdbc
	
	# Priority 1
	# /WEB-INF/classes/jdbc.properties
	jdbc_url=jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8&autoReconnect=true&autoReconnectForPools=true&failOverReadOnly=false
	jdbc_user=root
	jdbc_password=root
	#jdbc_driver=com.mysql.jdbc_Driver
	
	# Priority 2
	JAVA_OPTS="-Djdbc_url='...' -Djdbc_user=root -Djdbc_password=root"
	# run application
	
	# Priority 3
	export jdbc_url=...
	export jdbc_user=root
	export jdbc_password=root
	# run application