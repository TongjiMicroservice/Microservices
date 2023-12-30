#!/bin/bash

# API调用以获取token
response=$(curl -s -X POST 'https://open.feishu.cn/open-apis/auth/v3/app_access_token/internal' \
-H 'Content-Type: application/json' \
-d '{
        "app_id": "cli_a5f40dee38b9100c",
        "app_secret": "bfxhA82NwRuF6KSSJO2YQec3B6KTdWqf"
}')

# 提取tenant_access_token
token=$(echo $response | jq -r '.tenant_access_token')


# （可选）为了以后的使用，您可以将token保存到文件中
echo $token > meeting_token_file

