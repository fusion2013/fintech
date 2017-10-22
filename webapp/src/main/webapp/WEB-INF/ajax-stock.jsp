<!DOCTYPE html>
<html>
<head>
  <title>Live Stock Information</title>
  <style>
    #container {
      width: 500px;
      height: 500px;
      border: 2px solid black;
      border-radius: 10px;
      margin: 50px auto;
      padding: 20px;
    }

    h1 {
      text-align: center;
      color: royalblue;
    }

    table {
      width: 400px;
      height: 400px;
      margin: 0px auto;
      padding: 10px;
      background: lightsteelblue;
      border: 1px solid steelblue;
    }

    tr, td {
      padding: 10px;
      width: 50px;
      border: 1px solid aliceblue;
    }

    td {
      text-align: center;
      font-size: 22px;
    }
  </style>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

  <script>
      var temp = [];
      $(document).ready(function () {
          var tickerArray = [
              'AAPL',
              'AAAP'
          ];
          var randomNumber = Math.floor(Math.random()*tickerArray.length);
          stockInformation(tickerArray[randomNumber]);
		  setInterval( function() { stockInformation(tickerArray[randomNumber]); }, 5000 );


      });

      function stockInformation(val) {

          $.ajax({

              url: "<%=request.getContextPath()%>/getStock?ticker=" + val,
              type: 'GET',
              dataType: "json",
              success: function(data) {

                   var output = "<table>"
                                $.each(data, function (key, value) {

                                    output += "<tr><td>" + value.instId + "</td>"
										+ "<td>" + value.open + "</td>"
										+ "<td>" + value.high + "</td>"
										+ "<td>" + value.low + "</td>"
										+ "<td>" + value.close + "</td>"
										+ status + "</tr>";

                                })
                                output += "</table>";
                                $("#result").html(output);
              }, error: function() {
                alert("Error occurs.....");
              }

          });



      }
  </script>
</head>
<body>
<div id="container">
  <h1>NYSE Live</h1>
  <div id="result"></div>
</div>
</body>
</html>