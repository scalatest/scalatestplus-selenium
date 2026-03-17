#!/bin/bash
# Pre-download browsers using Selenium Manager
# This script triggers browser downloads by running tests for all supported browsers

set -e

echo "Setting up environment for browser downloads..."
export DISPLAY=:99
export SE_TIMEOUT=600

# Start Xvfb if not already running
if ! pgrep -x "Xvfb" > /dev/null; then
    echo "Starting Xvfb..."
    Xvfb -ac :99 -screen 0 1280x1024x24 > /dev/null 2>&1 &
    sleep 2
fi

echo "Triggering browser downloads via Selenium Manager..."
echo "This will download browsers that are not already installed."
echo "Note: Internet Explorer (Windows) and Safari (macOS) will be skipped on Linux."

# Trigger downloads for all supported browsers
# We use || true because we only care about the download, not test success

echo "Downloading Chrome..."
sbt "testOnly *DriverSpec* -- -z \"should work with Chrome\"" || true

echo "Downloading Firefox..."
sbt "testOnly *DriverSpec* -- -z \"should work with Firefox\"" || true

echo "Downloading Microsoft Edge..."
sbt "testOnly *DriverSpec* -- -z \"should work with Microsoft Edge\"" || true

# Skip IE and Safari on Linux as they are platform-specific
if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "win32" ]]; then
    echo "Downloading Internet Explorer (Windows)..."
    sbt "testOnly *DriverSpec* -- -z \"should work with Internet Explorer\"" || true
fi

if [[ "$OSTYPE" == "darwin"* ]]; then
    echo "Downloading Safari (macOS)..."
    sbt "testOnly *DriverSpec* -- -z \"should work with Safari\"" || true
fi

echo ""
echo "Browser download attempt completed."
echo "Browsers should now be cached in ~/.cache/selenium/"
echo ""
echo "Cache contents:"
ls -la ~/.cache/selenium/ 2>/dev/null || echo "No cache directory yet"