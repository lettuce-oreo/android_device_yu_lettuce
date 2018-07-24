sh device/yu/lettuce/patches/apply.sh
croot

export TARGET_USE_SDCLANG=true
export SDCLANG_FLAGS="-O3 -fvectorize -Wno-vectorizer-no-neon -Wno-user-defined-warnings"
export SDCLANG_FLAGS_2="-O3 -fvectorize -Wno-vectorizer-no-neon -Wno-user-defined-warnings"
export SDCLANG_COMMON_FLAGS="-O3 -fvectorize -Wno-vectorizer-no-neon -Wno-user-defined-warnings"

GCC_7_DIR=prebuilts/gcc/linux-x86/aarch64/aarch64-opt-linux-android
GCC_7_LINK="https://github.com/krasCGQ/aarch64-linux-android/"

if [ ! -d $GCC_7_DIR ]; then

  tput bold
  tput setaf 1
  echo -e "\nWARNING: GCC 7.x / Linaro 7.x not present at $GCC_7_DIR"

  wget -q --spider http://google.com

  if [ $? -eq 0 ]; then
    tput setaf 4
    echo -e "\nInternet connection detected"
    echo -e "\n=======================\nCloning Linaro 7.x ....\n=======================\n"
    git clone -b opt-linaro-7.x $GCC_7_LINK $GCC_7_DIR
  else
    tput setaf 1
    echo -e "You are not connected to the internet! Cannot clone Linaro 7.x automatically\n"
    echo -e "ERROR: Please clone Linaro 7.x from $GCC_7_LINK (opt-linaro-7.x branch) \nto $GCC_7_DIR, before continuing build !!\n"
    echo -e "Or else if it is present elsewhere, update the dir in BoardConfig.mk."
  fi

fi
