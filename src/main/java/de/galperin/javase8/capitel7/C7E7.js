function pipe(){
    var command = [
        '/bin/bash',
        '-c',
        Array.prototype.slice.call(arguments).join('|')
    ];
    var builder = new java.lang.ProcessBuilder(command);
    builder.directory(new java.io.File('./src/main/java/de/galperin/javase8/capitel7'));
    builder.redirectErrorStream(true);
    var process = builder.start();
    var is = process.getInputStream();
    var isr = new java.io.InputStreamReader(is);
    var br = new java.io.BufferedReader(isr);
    var line;
    while ((line = br.readLine()) != null) {
        print(line);
    }
}

pipe('find .', 'xargs grep function', 'sort -r');