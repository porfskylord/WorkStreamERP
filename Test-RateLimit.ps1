# Test-RateLimit.ps1
# PowerShell script to test API Gateway rate limiting with JWT authentication

# Configuration
$baseUrl = "http://localhost:8080"
$endpoint = "/api/users/test"
$jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhemFkIiwiaXNzIjoiUk9MRV9BRE1JTiIsImlhdCI6MTc1ODEzNDA2NywiZXhwIjoxNzU4MTM3NjY3fQ._K_GAnKFQys-yZZenlXcrILaPiy0Glq47zn5MKIXB6Y"

# Test parameters
$totalRequests = 50  # Increased to ensure we hit the rate limit
$delayBetweenRequests = 1  # milliseconds - very fast to hit rate limit
$maxConsecutiveFails = 5  # Stop after this many consecutive rate limits

# Colors for console output
$successColor = "Green"
$rateLimitColor = "Yellow"
$errorColor = "Red"
$infoColor = "Cyan"
$debugColor = "Gray"
$statsColor = "Magenta"

# Statistics
$stats = @{
    TotalRequests = 0
    SuccessCount = 0
    RateLimitedCount = 0
    ErrorCount = 0
    StartTime = Get-Date
}

# Function to make API request with JWT
function Invoke-ApiRequest {
    param (
        [string]$url,
        [int]$requestNumber,
        [string]$token
    )

    $stats.TotalRequests++

    try {
        $headers = @{
            "Authorization" = "Bearer $token"
            "Accept" = "application/json, text/plain, */*"
        }

        $response = Invoke-WebRequest -Uri $url -Method Get -Headers $headers -UseBasicParsing
        $statusCode = $response.StatusCode
        $remaining = $response.Headers["X-RateLimit-Remaining"]
        $reset = $response.Headers["X-RateLimit-Reset"]
        $limit = $response.Headers["X-RateLimit-Limit"]

        $message = "Request #$requestNumber - Status: $statusCode"
        if ($remaining) { $message += " | Remaining: $remaining/$limit" }
        if ($reset) { $message += " | Reset in: ${reset}s" }

        if ($statusCode -eq 200) {
            $stats.SuccessCount++
            Write-Host $message -ForegroundColor $successColor
            return $true
        }
        else {
            Write-Host $message -ForegroundColor $rateLimitColor
            return $false
        }
    }
    catch {
        $statusCode = $_.Exception.Response.StatusCode.value__
        $message = "Request #$requestNumber - Status: $statusCode"

        if ($statusCode -eq 429) {
            $stats.RateLimitedCount++
            $retryAfter = $_.Exception.Response.Headers["Retry-After"]
            if ($retryAfter) {
                $message += " | Retry after: ${retryAfter}s"
                # Wait for the rate limit to reset
                Write-Host "Waiting for rate limit to reset ($retryAfter seconds)..." -ForegroundColor $rateLimitColor
                Start-Sleep -Seconds $retryAfter
            }
            Write-Host $message -ForegroundColor $rateLimitColor
            return $false
        }
        else {
            $stats.ErrorCount++
            $errorMsg = "$message - Error: $($_.Exception.Message)"
            if ($_.ErrorDetails) {
                $errorMsg += "`nDetails: $($_.ErrorDetails)"
            }
            Write-Host $errorMsg -ForegroundColor $errorColor
            return $false
        }
    }
}

# Main script execution
Write-Host "`n=== Starting Rate Limit Test ===" -ForegroundColor $infoColor
Write-Host "Base URL: $baseUrl" -ForegroundColor $infoColor
Write-Host "Endpoint: $endpoint" -ForegroundColor $infoColor
Write-Host "Total requests: $totalRequests" -ForegroundColor $infoColor
Write-Host "Delay between requests: ${delayBetweenRequests}ms" -ForegroundColor $infoColor
Write-Host "Max consecutive fails before stopping: $maxConsecutiveFails" -ForegroundColor $infoColor
Write-Host ("-" * 50) -ForegroundColor $infoColor

# Run the test
$consecutiveFails = 0
for ($i = 1; $i -le $totalRequests; $i++) {
    $success = Invoke-ApiRequest -url "$baseUrl$endpoint" -requestNumber $i -token $jwtToken

    if (-not $success) {
        $consecutiveFails++
        if ($consecutiveFails -ge $maxConsecutiveFails) {
            Write-Host "`nStopping test after $maxConsecutiveFails consecutive failures" -ForegroundColor $errorColor
            break
        }
    } else {
        $consecutiveFails = 0
    }

    # Add small delay between requests if not rate limited
    if ($i -lt $totalRequests -and $consecutiveFails -eq 0) {
        Start-Sleep -Milliseconds $delayBetweenRequests
    }
}

# Calculate test duration
$duration = (Get-Date) - $stats.StartTime
$requestsPerSecond = [math]::Round($stats.TotalRequests / $duration.TotalSeconds, 2)

# Print summary
Write-Host "`n=== Test Summary ===" -ForegroundColor $statsColor
Write-Host "Total runtime: $($duration.ToString('hh\:mm\:ss\.fff'))" -ForegroundColor $statsColor
Write-Host "Total requests: $($stats.TotalRequests)" -ForegroundColor $statsColor
Write-Host "Successful requests: $($stats.SuccessCount)" -ForegroundColor $successColor
Write-Host "Rate limited requests: $($stats.RateLimitedCount)" -ForegroundColor $rateLimitColor
Write-Host "Error requests: $($stats.ErrorCount)" -ForegroundColor $errorColor
Write-Host ("-" * 50) -ForegroundColor $statsColor
Write-Host "Requests per second: $requestsPerSecond" -ForegroundColor $statsColor
Write-Host ("-" * 50) -ForegroundColor $statsColor
Write-Host "Test completed!" -ForegroundColor $infoColor
