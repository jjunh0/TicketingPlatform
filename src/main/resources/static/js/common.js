// basic-N2 [CbLVQR1Eay]
(function() {
  $(function() {
    $(".basic-N2").each(function() {
      const $block = $(this);
      const $dim = $block.find('.header-dim');
      // Header Scroll
      $(window).on("load scroll", function() {
        const $thisTop = $(this).scrollTop();
        if ($thisTop > 120) {
          $block.addClass("header-top-active");
        } else {
          $block.removeClass("header-top-active");
        }
      });
      // Header Mobile 1Depth Click
      if (window.innerWidth <= 992) {
        $block.find(".header-gnbitem").each(function() {
          const $gnblink = $(this).find(".header-gnblink");
          const $sublist = $(this).find(".header-sublist");
          if ($sublist.length) {
            $gnblink.attr("href", "javascript:void(0);");
          }
        });
      }
      // Mobile Lang
      $block.find('.header-langbtn').on('click', function() {
        $(this).parent().toggleClass('lang-active');
      });
      // Mobile Top
      $block.find('.btn-momenu').on('click', function() {
        $block.addClass('momenu-active');
        $dim.fadeIn();
      });
      $block.find('.btn-close, .header-dim').on('click', function() {
        $block.removeClass('momenu-active');
        $dim.fadeOut();
      });
      // Mobile Gnb
      $block.find('.header-gnbitem').each(function() {
        const $this = $(this);
        const $thislink = $this.find('.header-gnblink');
        $thislink.on('click', function() {
          if (!$(this).parent().hasClass('item-active')) {
            $('.header-gnbitem').removeClass('item-active');
          }
          $(this).parents(".header-gnbitem").toggleClass("item-active");
        });
      });
    });
  });
})();
// basic-N37 [FRLuQynxaj]
$(document).ready(function() {
  let signup = $(".links").find("li").find("#signup");
  let signin = $(".links").find("li").find("#signin");
  let first_input = $("form").find(".first-input");
  let hidden_input = $("form").find(".input__block").find("#repeat__password");
  let signin_btn = $("form").find(".signin__btn");
  let id_input = $("form").find(".input__block").find("#id");
  let email_input = $("form").find(".input__block").find("#memberemail");
  let name_input = $("form").find(".input__block").find("#membername");
  //----------- sign up ---------------------
  signup.on("click", function(e) {
    e.preventDefault();
    $(this).parent().parent().siblings("h1").text("SIGN UP");
    $(this).parent().css("opacity", "1");
    $(this).parent().siblings().css("opacity", ".6");

    // Display all fields for signup
    id_input.css({
      "opacity": "1",
      "display": "block"
    });
    email_input.css({
      "opacity": "1",
      "display": "block"
    });
    name_input.css({
      "opacity": "1",
      "display": "block"
    });
    hidden_input.css({
      "opacity": "1",
      "display": "block"
    });
    signin_btn.text("Sign up");
  });

  //----------- sign in ---------------------
  signin.on("click", function(e) {
    e.preventDefault();
    $(this).parent().parent().siblings("h1").text("SIGN IN");
    $(this).parent().css("opacity", "1");
    $(this).parent().siblings().css("opacity", ".6");

    // Display only ID and password for signin
    id_input.css({
      "opacity": "1",
      "display": "block"
    });
    email_input.css({
      "opacity": "0",
      "display": "none"
    });
    name_input.css({
      "opacity": "0",
      "display": "none"
    });
    hidden_input.css({
      "opacity": "0",
      "display": "none"
    });
    signin_btn.text("Sign in");
  });




});

function login() {
  const email = document.getElementById('memberemail').value;
  const password = document.getElementById('password').value;

  fetch('/api/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ email: email, password: password })
  })
      .then(response => response.json())
      .then(data => {
        if (data.success) {
          window.location.href = '/home.html';
        } else {
          alert('존재하지 않는 계정입니다');
        }
      })
      .catch(error => console.error('Error:', error));
}
/*
// basic-N11 [SHlvv7XJ9G]
let selectedSeats = [];

function selectSeat(seat, seatId) {
  if (!seat.classList.contains('occupied')) {
    seat.classList.toggle('selected');
    if (seat.classList.contains('selected')) {
      selectedSeats.push(seatId);
    } else {
      selectedSeats = selectedSeats.filter(id => id !== seatId);
    }
  }
}

function submitSeats() {
  if (selectedSeats.length > 0) {
    const url = '/reserveSeats';
    fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          seats: selectedSeats
        })
      })
      .then(response => response.json())
      .then(data => alert('Booking successful: ' + data.message))
      .catch(error => console.error('Error:', error));
  } else {
    alert('No seats selected.');
  }
}
*/


document.getElementById("commentForm").addEventListener("submit", function(event) {
  event.preventDefault();

  const formData = new FormData(this);
  const actionUrl = this.getAttribute("th:action");

  fetch(actionUrl, {
    method: "POST",
    body: formData,
  })
      .then(response => response.json())
      .then(data => {
        if (data.success) {

          const commentList = document.getElementById("commentList");
          const newComment = document.createElement("div");
          newComment.className = "comment-item";
          newComment.innerHTML = `
          <div class="comment-info">
            <span class="comment-author">${data.comment.author}</span>
          </div>
          <div class="comment-content">
            <p>${data.comment.content}</p>
          </div>
          <div class="comment-actions">
            <button class="edit-btn">수정</button>
            <button class="delete-btn">삭제</button>
          </div>
          <hr>
        `;
          commentList.appendChild(newComment);

          document.getElementById("commentForm").reset();
        } else {
          alert("댓글을 등록하는 데 실패했습니다.");
        }
      })
      .catch(error => {
        console.error("Error:", error);
        alert("댓글을 등록하는 도중 오류가 발생했습니다.");
      });
});

