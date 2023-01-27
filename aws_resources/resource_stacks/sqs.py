import aws_cdk as cdk

from aws_cdk import (
    core,
    aws_sqs as _sqs,
    aws_sns_subscriptions as _subs
)

class SqsTemplateStack(core.Stack):

    def __init__(self, scope: core.Construct, construct_id: str, **kwargs) -> None:
        super().__init__(scope, construct_id, **kwargs)

        # Create SQS queue
        self.sqs_queue = _sqs.Queue(
            self,
            "sqsQueueId",
            queue_name="newOrderQueue"
        )
      
       # Echo queue arn
        core.CfnOutput(
            self,
            "albDNS",
            value=f"{self.sqs_queue.queue_url}",
            description="SQS queue arn"
        )

