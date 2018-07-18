sh device/yu/lettuce/patches/apply.sh
croot

export TARGET_USE_SDCLANG=true
export SDCLANG_FLAGS="-O3 -fvectorize-loops -Wno-vectorizer-no-neon -Wno-user-defined-warnings"
export SDCLANG_FLAGS_2="-O3 -fvectorize-loops -Wno-vectorizer-no-neon -Wno-user-defined-warnings"
export SDCLANG_COMMON_FLAGS="-O3 -fvectorize-loops -Wno-vectorizer-no-neon -Wno-user-defined-warnings"
