#!/usr/bin/env bash
#if [[ $SPL_BUILD_NATIVE == "false" ]]; then
#    echo "Skipping the native image build because SPL_BUILD_NATIVE is set to false."
#    exit 0
#fi
$JAVA_HOME/bin/native-image --tool:truffle -H:MaxRuntimeCompileMethods=1200 \
    -cp ../language/target/spl.jar:../launcher/target/launcher-1.0-SNAPSHOT.jar \
    com.spl.launcher.SplLauncher \
    splnative
