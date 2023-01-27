# Project highlights how using a Python Lambda and Java REST API the following:
#   - Create AWS infrastructure using Python and CDK
#   - Create a Lambda function that is a topic listener, which in turn publishes message to an SQS queue
#       - This use case shows how message can be further processed as opposed to subscribing queue directly to SNS topic
#   - From REST API, publish message to a topic using AWS SDK
#   - Also from same REST API (for project simplicity) implement an SQS queue listener using AWS SDK

# Project creates AWS artifacts:
#  - SNS Topic
#  - SQS Queue
#  - Python Lambda function subscribed to Topic
#  - A sample Java-based REST API for publishing to Topic and for listening to Queue

# When deployed, issue a PUT to URL:
#    http://localhost:1025/order

# PUT request body example:
# {
#    "orderId" : "9",
#    "customerId" : "999",
#    "sku" : "110-rrgt555",
#    "quantity" : 5
# }


Requirements:
 - A command shell such as Git Bash
 - Java and an IDE (IntelliJ!)
 - Python
 - CDK
 - Node JS/NPM for miscellaneous package installs
 
