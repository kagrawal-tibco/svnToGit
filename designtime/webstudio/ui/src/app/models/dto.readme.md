The `dto.ts` is generated (even though indirectly) from `xsd` files inside `sb-rms/src/resources/schemas`. 
It's not checked-in to the repository. Therefore if you just cloned the repository and try to build the UI,
it's very likely to fail because of the missing of `dto.ts`. To obtain `dto.ts`, you can either 

* run `mvn package` in `sb-rms` directory. This will make a complete build, including the server and the UI.
`dto.ts` will be generated during the build.
* run `mvn process-classes` in `sb-rms` directory. This will only build the server and generate `dto.ts`.

Please make sure to re-generate `dto.ts` every time you have made changes to the schama files.