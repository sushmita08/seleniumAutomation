
const http = require("http");
const express = require("express");
const bodyParser = require("body-parser");
const cors = require("cors");
const { exec } = require("child_process");
const app = express();
const host = "localhost";
const port = 8000;
const server = http.createServer(app);
app.use(cors());

app.get("/", function (req, res) {
  exec("sh tbiRtoDesktop.sh > outputtbi.txt", (error, stdout, stderr) => {
    if (error) {
      console.log(`error: ${error.message}`);
      return;
    }
    if (stderr) {
      console.log(`stderr: ${stderr}`);
      return res.json(stderr);
    }
    console.log(`stdout: ${stdout}`);
    res.json(stdout);
  });
});

app.get("/kohls", function (req, res) {
  exec("sh kohlsUrgencyDesktop.sh > outputkohls.txt", (error, stdout, stderr) => {
    if (error) {
      console.log(`error: ${error.message}`);
      return;
    }
    if (stderr) {
      console.log(`stderr: ${stderr}`);
      return res.json(stderr);
    }
    console.log(`stdout: ${stdout}`);
    res.json(stdout);
  });
});



app.listen(port, () => {
  console.log(`Server is running on http://${host}:${port}`);
});

