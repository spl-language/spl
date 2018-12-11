#!/usr/bin/env bash
#if [[ $TONE_BUILD_NATIVE == "false" ]]; then
#    echo "Skipping the native image build because TONE_BUILD_NATIVE is set to false."
#    exit 0
#fi
$JAVA_HOME/bin/native-image --tool:truffle -H:MaxRuntimeCompileMethods=1200 \
    -cp ../language/target/tone.jar:../launcher/target/launcher-1.0-SNAPSHOT.jar \
    com.tone.launcher.ToneLauncher \
    tonenative
