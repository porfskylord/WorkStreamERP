param(
    [string]$Command
)

switch ($Command) {
    "build"   { docker compose build --no-cache }
    "up"      { docker compose up -d }
    "down"    { docker compose down }
    "logs"    { docker compose logs -f }
    "restart" { docker compose down; docker compose up -d }
    default   { Write-Host "Usage: .\start.ps1 {build|up|down|logs|restart}" }
}