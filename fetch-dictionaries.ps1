# the origin source
$url = "http://ftp.edrdg.org/pub/Nihongo/JMdict.gz"
$outputPath = "./src/main/resources/dicts/"

if(!(Test-Path -Path $outputPath)) {
    New-Item -ItemType Directory -Path $outputPath | Out-Null
}

Write-Host "Downloading $url"
Invoke-WebRequest -Uri $url -OutFile "$outputPath/JMdict.gz"

Write-Host "Extracting JMdict.gz to $outputPath"

# Check 7-Zip whether installed
if (-not (Get-Command 7z.exe -ErrorAction SilentlyContinue)) {
    Write-Error "7-Z is not installed or not added to the system PATH environment variable."
}

7z x "$outputPath/JMdict.gz" -o"$outputPath" -y

Remove-Item -Path "$outputPath/JMdict.gz"

Write-Host "JMdict.gz extracted to $outputPath"