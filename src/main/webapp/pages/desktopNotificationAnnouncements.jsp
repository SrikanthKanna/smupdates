<html class="gr__output_jsbin_com"><head><script type="text/javascript" src="chrome-extension://aadgmnobpdmgmigaicncghmmoeflnamj/ng-inspector.js"></script></head>
<body data-gr-c-s-loaded="true"><button onclick="notifyMe()">
  Notify me!
</button>
<div id="ann"></div>
</body>

<script type="text/javascript" async="" src="https://www.google-analytics.com/plugins/ua/linkid.js"></script>
 <script type = "text/javascript"   src = "https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

<script>
 setTimeout(function () {
            location.reload()
        }, 20000);
document.addEventListener('DOMContentLoaded', function () {
  if (Notification.permission !== "granted")
    Notification.requestPermission();
});
httpGet()

function httpGet()
{
   $.ajax({
    'url' : '/latestAnnouncements/',
    'type' : 'GET',
    'content-type':'application/json',
    'success' : function(data) {
	$("#ann").val(data)
		notifyMe();
	    }
  });
}

function notifyMe() {
  if (!Notification) {
    alert('Desktop notifications not available in your browser. Try Chromium.');
    return;
  }

  if ($("#ann").val() != "")
   {
	data =$("#ann").val();
	 for (var key in data) {
    var notification = new Notification('Hi There! You got Notification', {
      icon: 'http://wfarm2.dataknet.com/static/resources/icons/set54/f745bd06.png',
      body:data[key],
    });

    notification.onclick = function () {
      window.open("/dna/"+data[key]);
    };
	}
  }

}

</script>