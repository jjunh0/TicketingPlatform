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
  //----------- sign up ---------------------
  signup.on("click", function(e) {
    e.preventDefault();
    $(this).parent().parent().siblings("h1").text("SIGN UP");
    $(this).parent().css("opacity", "1");
    $(this).parent().siblings().css("opacity", ".6");
    first_input.removeClass("first-input__block").addClass("signup-input__block");
    hidden_input.css({
      "opacity": "1",
      "display": "block"
    });
    id_input.css({ // 추가
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
    first_input.addClass("first-input__block").removeClass("signup-input__block");
    hidden_input.css({
      "opacity": "0",
      "display": "none"
    });
    id_input.css({ // 추가
      "opacity": "0",
      "display": "none"
    });
    signin_btn.text("Sign in");
  });
});

// basic-N11 [SHlvv7XJ9G]
const movieSelect = document.getElementById('movie');
const container = document.querySelector('.container');
const seats = document.querySelectorAll('.row .seat:not(.occupied)');
const count = document.getElementById('count');
const total = document.getElementById('total');
let ticketPrice = +movieSelect.value;
populateUI();

function populateUI() {
  const selectedSeats = JSON.parse(localStorage.getItem('selectedSeats'));
  if (selectedSeats !== null && selectedSeats.length > 0) {
    selectedSeats.forEach((seatIndex) => {
      seats[seatIndex].classList.add('selected');
    });
  }
  const selectedMovieIndex = localStorage.getItem('selectedMovieIndex');
  if (selectedMovieIndex !== null) {
    movieSelect.selectedIndex = selectedMovieIndex;
  }
}

function setMovieData(movieIndex, moviePrice) {
  localStorage.setItem('selectedMovieIndex', movieIndex);
  localStorage.setItem('selectedMoviePrice', moviePrice);
}

function updateSelectedCount() {
  const selectedSeats = document.querySelectorAll('.row .selected');
  const selectedSeatCount = +selectedSeats.length;
  const selectedSeatsIndex = [...selectedSeats].map((seat) => [...seats].indexOf(seat));
  localStorage.setItem('selectedSeats', JSON.stringify(selectedSeatsIndex));
  count.textContent = selectedSeatCount;
  total.textContent = selectedSeatCount * ticketPrice;
}
movieSelect.addEventListener('change', (event) => {
  ticketPrice = +event.target.value;
  setMovieData(event.target.selectedIndex, event.target.value);
  updateSelectedCount();
});
container.addEventListener('click', (event) => {
  if (event.target.classList.contains('seat') && !event.target.classList.contains('occupied')) {
    event.target.classList.toggle('selected');
    updateSelectedCount();
  }
});
updateSelectedCount();