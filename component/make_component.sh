#!/usr/bin/env bash

COMPONENT_DIR="component_temp_dir"
LANGUAGE_PATH="$COMPONENT_DIR/jre/languages/tone"
if [[ -f ../native/tonenative ]]; then
    INCLUDE_SLNATIVE="TRUE"
fi

rm -rf COMPONENT_DIR

mkdir -p "$LANGUAGE_PATH"
cp ../language/target/tone.jar "$LANGUAGE_PATH"

mkdir -p "$LANGUAGE_PATH/launcher"
cp ../launcher/target/tone-launcher.jar "$LANGUAGE_PATH/launcher/"

mkdir -p "$LANGUAGE_PATH/bin"
cp ../tone $LANGUAGE_PATH/bin/
if [[ $INCLUDE_SLNATIVE = "TRUE" ]]; then
    cp ../native/tonenative $LANGUAGE_PATH/bin/
fi

mkdir -p "$COMPONENT_DIR/META-INF"
MANIFEST="$COMPONENT_DIR/META-INF/MANIFEST.MF"
touch "$MANIFEST"
echo "Bundle-Name: Tone" >> "$MANIFEST"
echo "Bundle-Symbolic-Name: com.tone" >> "$MANIFEST"
echo "Bundle-Version: 1.0.0-rc9" >> "$MANIFEST"
echo 'Bundle-RequireCapability: org.graalvm; filter:="(&(graalvm_version=1.0.0-rc9)(os_arch=amd64))"' >> "$MANIFEST"
echo "x-GraalVM-Polyglot-Part: True" >> "$MANIFEST"

cd $COMPONENT_DIR
jar cfm ../tone-component.jar META-INF/MANIFEST.MF .

echo "bin/tone = ../jre/languages/tone/bin/tone" > META-INF/symlinks
if [[ $INCLUDE_SLNATIVE = "TRUE" ]]; then
    echo "bin/tonenative = ../jre/languages/tone/bin/tonenative" >> META-INF/symlinks
fi
jar uf ../tone-component.jar META-INF/symlinks

echo "jre/languages/tone/bin/tone = rwxrwxr-x" > META-INF/permissions
echo "jre/languages/tone/bin/tonenative = rwxrwxr-x" >> META-INF/permissions
jar uf ../tone-component.jar META-INF/permissions
cd ..
rm -rf $COMPONENT_DIR
