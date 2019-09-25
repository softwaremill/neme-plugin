#!/usr/bin/env bash

if [[ "$TRAVIS_PULL_REQUEST" == "false" ]]; then
    openssl aes-256-cbc -K $encrypted_7cc652e0dcd8_key -iv $encrypted_7cc652e0dcd8_iv -in secrets.tar.enc -out secrets.tar -d
    tar xvf secrets.tar
fi
