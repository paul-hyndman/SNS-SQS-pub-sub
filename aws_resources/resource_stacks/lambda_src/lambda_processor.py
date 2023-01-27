import json
import logging
import os
import boto3
client = boto3.client('sqs')

# A simple SNS topic listener that publishes to an SQS queue.  
# More sophisticated implementations could follow AWS Message bus pattern or use an Event Bridge

def lambda_handler(event, context):
    logging.getLogger().setLevel(level=os.getenv('LOG_LEVEL', 'DEBUG').upper())
    queue_url = os.getenv('queue_url')
    message = event['Records'][0]['Sns']['Message']

    # Opportunity here to direct to specific queue, amend message content, or other processing

    print(message)
    client.send_message(
        QueueUrl=queue_url,
        MessageBody=(message)
    )

    return {
        "statusCode:": 200,
        "message": message
    }