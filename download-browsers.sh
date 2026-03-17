#!/bin/bash
# Pre-download browsers using Selenium Manager
# This script triggers browser downloads by running the Edge test

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

# Trigger Edge download by running the Edge test
# We use || true because we only care about the download, not test success
echo "Downloading Edge..."
sbt "testOnly *DriverSpec* -- -z \"should work with Microsoft Edge\"" || true

echo "Browser download attempt completed."
echo "Browsers should now be cached in ~/.cache/selenium/"
ls -la ~/.cache/selenium/ 2>/dev/null || echo "No cache directory yet"