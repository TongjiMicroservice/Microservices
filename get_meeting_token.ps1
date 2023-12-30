# 定义API请求的参数
$uri = 'https://open.feishu.cn/open-apis/auth/v3/app_access_token/internal'
$body = @{
    app_id = 'cli_a5f40dee38b9100c'
    app_secret = 'bfxhA82NwRuF6KSSJO2YQec3B6KTdWqf'
} | ConvertTo-Json

# 发送请求
$response = Invoke-RestMethod -Uri $uri -Method Post -Body $body -ContentType 'application/json'

# 提取并输出tenant_access_token
$token = $response.tenant_access_token

# 保存token到文件
$token | Out-File -FilePath 'meeting_token_file'


