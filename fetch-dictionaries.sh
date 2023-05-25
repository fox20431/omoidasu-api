#!/bin/bash

echo "Fetch dictionaries started."

url="http://ftp.edrdg.org/pub/Nihongo/JMdict.gz"
outputPath="./"

if [ -e "$outputPath/JMdict" ]; then
  echo "JMdict already exists in $outputPath"
  exit 1
fi

if [ ! -d "$outputPath" ]; then
  mkdir -p "$outputPath"
fi

echo "Downloading $url"
curl -o "$outputPath/JMdict.gz" "$url"

echo "Extracting JMdict.gz to $outputPath"

# Check 7-Zip whether installed
if ! command -v 7z &>/dev/null; then
  echo "7zip is not installed. Installing now..."
  if [ "$(uname)" == "Darwin" ]; then
    # Mac OS X
    brew install p7zip
  elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
    # Linux
    # TODO I need more detailed method to compatible with more OS
    sudo apt-get update
    sudo apt-get install p7zip-full
  else
    echo "Unsupported operating system."
    exit 1
  fi
  exit 1
fi

7z x "$outputPath/JMdict.gz" -o"$outputPath" -y

rm "$outputPath/JMdict.gz"

echo "JMdict.gz extracted to $outputPath"
