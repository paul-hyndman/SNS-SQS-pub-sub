#!/usr/bin/env python3
import os

from aws_cdk import core as cdk
from resource_stacks.sns import SnsTemplateStack
from resource_stacks.sqs import SqsTemplateStack
from resource_stacks.custom_lambda import CustomLambdaStack

app = cdk.App()
snsTemplateStack = SnsTemplateStack(app, "SnsTemplateStack")
sqsTemplateStack = SqsTemplateStack(app, "SqsTemplateStack")
CustomLambdaStack(app, "CustomLambdaStack", snsTemplateStack.custom_topic, sqsTemplateStack.sqs_queue)
app.synth()
