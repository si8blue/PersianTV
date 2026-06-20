FROM mcr.microsoft.com/devcontainers/universal:latest

ENV ANDROID_HOME=/opt/android-sdk
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools

USER root

RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    lib32stdc++6 \
    lib32z1 \
    && rm -rf /var/lib/apt/lists/*

RUN wget -q https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip -O /tmp/cmdline-tools.zip \
    && mkdir -p $ANDROID_HOME/cmdline-tools \
    && unzip -q /tmp/cmdline-tools.zip -d /tmp \
    && mv /tmp/cmdline-tools $ANDROID_HOME/cmdline-tools/latest \
    && rm /tmp/cmdline-tools.zip

RUN yes | $ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager --licenses --sdk_root=$ANDROID_HOME 2>/dev/null \
    && $ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager \
    'platforms;android-34' \
    'build-tools;34.0.0' \
    'platform-tools' \
    --sdk_root=$ANDROID_HOME 2>/dev/null

USER vscode

RUN echo "sdk.dir=$ANDROID_HOME" > /home/vscode/workspace/local.properties

CMD ["bash"]
