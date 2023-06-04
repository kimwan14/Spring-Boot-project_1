"use strict";
$('input[type="password"]')
  .on("focus", function () {
    $("*").addClass("password");
  })
  .on("focusout", function () {
    $("*").removeClass("password");
  });
window !== window.top && u();
(function () {
  function a() {
    $("body").mousemove(function (a) {
      m = a.clientX;
      p = a.clientY;
      t = Date.now();
      n || l();
    });
    $(window)
      .on("blur mouseout", function () {
        p = m = null;
      })
      .on("resize", function () {
        d && d.parentNode && d.parentNode.removeChild(d);
        b();
      });
    b();
  }

  function b() {
    var a = null == g ? !0 : !1;
    d = document.createElement("canvas");
    d.width = $(window).width();
    d.height = $(window).height();
    $(".dark").append(d);
    g = document.createElement("canvas");
    g.width = $(window).width();
    g.height = $(window).height();
    if (d.getContext && d.getContext("2d")) {
      h = d.getContext("2d");
      e = g.getContext("2d");
      e.lineCap = "round";
      e.shadowColor = "#000000";
      e.shadowBlur = 30;
      c = new Image();
      var b = $(".dark").css("background-image");
      b &&
        ($(c).one("load", function () {
          a && l();
        }),
        (b = "https://s31.postimg.org/xzx86mv2v/coa1.jpg"),
        /*b.replace(/url\((.*)\)/, "$1").replace(/["']/gi, "").replace(/\.jpg/, "_color.jpg")*/ $(
          c
        ).attr("src", b),
        (b = $(
          '\x3cdiv style\x3d"position:absolute;height:0;width:0;overflow:hidden;"\x3e\x3c/div\x3e'
        )),
        $("body").append(b),
        b.append(c));
    }
  }

  function l() {
    var a,
      b = Date.now();
    n = b > t + 500 ? !1 : !0;
    m &&
      n &&
      f.splice(0, 0, {
        time: b,
        x: m,
        y: p
      });
    for (a = 0; a < f.length; )
      1e3 < b - f[a].time ? f.splice(a, f.length) : a++;
    0 < f.length && window.j(l);
    e.clearRect(0, 0, g.width, g.height);
    for (a = 1; a < f.length; a++) {
      var v = Math.sqrt(
        Math.pow(f[a].x - f[a - 1].x, 2) + Math.pow(f[a].y - f[a - 1].y, 2)
      );
      e.strokeStyle =
        "rgba(0,0,0," + Math.max(1 - (b - f[a].time) / 1e3, 0) + ")";
      e.lineWidth = 65 + 65 * Math.max(1 - v / 50, 0);
      e.beginPath();
      e.moveTo(f[a - 1].x, f[a - 1].y);
      e.lineTo(f[a].x, f[a].y);
      e.stroke();
    }
    a = d.width;
    b = (d.width / c.naturalWidth) * c.naturalHeight;
    b < d.height &&
      ((b = d.height), (a = (d.height / c.naturalHeight) * c.naturalWidth));
    h.drawImage(c, 0, 0, a, b);
    h.globalCompositeOperation = "destination-in";
    h.drawImage(g, 0, 0);
    h.globalCompositeOperation = "source-over";
  }
  var d,
    g,
    h,
    e,
    c,
    m = null,
    p = null,
    f = [],
    t = 0,
    n = !0;
  "createTouch" in document || $(a);
  window.j = (function () {
    return (
      window.requestAnimationFrame ||
      window.webkitRequestAnimationFrame ||
      window.mozRequestAnimationFrame ||
      window.oRequestAnimationFrame ||
      window.msRequestAnimationFrame ||
      function (a) {
        window.setTimeout(a, 1e3 / 60);
      }
    );
  })();
})();
