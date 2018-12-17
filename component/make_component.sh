#!/usr/bin/env bash

COMPONENT_DIR="component_temp_dir"
LANGUAGE_PATH="$COMPONENT_DIR/jre/languages/spl"
if [[ -f ../native/splnative ]]; then
    INCLUDE_SLNATIVE="TRUE"
fi

rm -rf COMPONENT_DIR

mkdir -p "$LANGUAGE_PATH"
cp ../language/target/spl.jar "$LANGUAGE_PATH"

mkdir -p "$LANGUAGE_PATH/launcher"
cp ../launcher/target/spl-launcher.jar "$LANGUAGE_PATH/launcher/"

mkdir -p "$LANGUAGE_PATH/bin"
cp ../spl $LANGUAGE_PATH/bin/
if [[ $INCLUDE_SLNATIVE = "TRUE" ]]; then
    cp ../native/splnative $LANGUAGE_PATH/bin/
fi

mkdir -p "$COMPONENT_DIR/META-INF"
MANIFEST="$COMPONENT_DIR/META-INF/MANIFEST.MF"
touch "$MANIFEST"
echo "Bundle-Name: Tone" >> "$MANIFEST"
echo "Bundle-Symbolic-Name: com.spl" >> "$MANIFEST"
echo "Bundle-Version: 1.0.0-rc9" >> "$MANIFEST"
echo 'Bundle-RequireCapability: org.graalvm; filter:="(&(graalvm_version=1.0.0-rc9)(os_arch=amd64))"' >> "$MANIFEST"
echo "x-GraalVM-Polyglot-Part: True" >> "$MANIFEST"

cd $COMPONENT_DIR
jar cfm ../spl-component.jar META-INF/MANIFEST.MF .

echo "bin/spl = ../jre/languages/spl/bin/spl" > META-INF/symlinks
if [[ $INCLUDE_SLNATIVE = "TRUE" ]]; then
    echo "bin/splnative = ../jre/languages/spl/bin/splnative" >> META-INF/symlinks
fi
jar uf ../spl-component.jar META-INF/symlinks

echo "jre/languages/spl/bin/spl = rwxrwxr-x" > META-INF/permissions
echo "jre/languages/spl/bin/splnative = rwxrwxr-x" >> META-INF/permissions
jar uf ../spl-component.jar META-INF/permissions
cd ..
rm -rf $COMPONENT_DIR
