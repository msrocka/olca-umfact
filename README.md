# olca-native-ptr
This small library that supports to pass around pointers to factorized matrices
between Java and native code. The glue code is written in C but we may want
to rewrite this experiment in Rust so that we can integrate this into
[olca-rust](https://github.com/msrocka/olca-rust).

The idea is to allocate a factorized matrix on the native site and return
the pointer to the Java site:

![](principle.png)


For Rust, returning a pointer to a Box as described here could work:
https://codereview.stackexchange.com/a/223754