#!/bin/bash
echo "Compiling Phantom Detective Game..."
find src -name "*.java" > sources.txt
javac -d out @sources.txt
if [ $? -eq 0 ]; then
    echo "Compilation successful!"
else
    echo "Compilation failed. Check errors above."
    exit 1
fi
