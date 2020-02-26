# Log-factorial

Illustration of the log-factorial function as a tail recursion. Again, this is just a single Scala file with no dependencies, so will just `sbt run`.

To run with command line arguments, pass them in. From the `sbt` prompt, do, say, `run 100000`. From the OS prompt, do
```bash
sbt "run 100000"
```

Try a non-tail-recursive version of the log-factorial function. Check that it works correctly for small argument, and that it overflows the stack for large arguments, like 100000.

