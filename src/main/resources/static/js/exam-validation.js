/**
 * Java 시험 페이지 유효성 검증 스크립트
 * SweetAlert2를 사용하여 에러 메시지 표시
 */
document.addEventListener("DOMContentLoaded", function () {
  // 서버 측 에러 메시지가 있을 경우 SweetAlert2로 표시
  const errorElement = document.querySelector(".alert-danger span");
  if (errorElement) {
    const errorMessage = errorElement.textContent.trim();
    Swal.fire({
      icon: "error",
      title: "시험 제출 실패",
      text: errorMessage,
      confirmButtonText: "확인",
      confirmButtonColor: "#0d6efd",
    });
  }

  // 클라이언트 사이드 유효성 검증
  const examForm = document.getElementById("examForm");
  if (!examForm) return;

  examForm.addEventListener("submit", function (e) {
    e.preventDefault();

    const userName = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    // 이름 검증
    if (!userName) {
      Swal.fire({
        icon: "warning",
        title: "이름을 입력해주세요",
        confirmButtonText: "확인",
        confirmButtonColor: "#0d6efd",
      }).then(() => document.getElementById("name").focus());
      return;
    }

    // 이메일 검증
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!email) {
      Swal.fire({
        icon: "warning",
        title: "이메일을 입력해주세요",
        confirmButtonText: "확인",
        confirmButtonColor: "#0d6efd",
      }).then(() => document.getElementById("email").focus());
      return;
    }
    if (!emailPattern.test(email)) {
      Swal.fire({
        icon: "warning",
        title: "이메일 형식 오류",
        text: "올바른 이메일을 입력해 주세요.",
        confirmButtonText: "확인",
        confirmButtonColor: "#0d6efd",
      }).then(() => document.getElementById("email").focus());
      return;
    }

    // 비밀번호 검증
    if (!password) {
      Swal.fire({
        icon: "warning",
        title: "비밀번호를 입력해주세요",
        confirmButtonText: "확인",
        confirmButtonColor: "#0d6efd",
      }).then(() => document.getElementById("password").focus());
      return;
    }

    // 답안 검증 (1~3번: 객관식, 4~5번: 주관식)
    for (let i = 1; i <= 3; i++) {
      const answer = document.querySelector(`input[name="answer${i}"]:checked`);
      if (!answer) {
        Swal.fire({
          icon: "warning",
          title: `${i}번 문제를 선택해주세요`,
          confirmButtonText: "확인",
          confirmButtonColor: "#0d6efd",
        });
        return;
      }
    }

    // 주관식 답안 검증 (4~5번)
    for (let i = 4; i <= 5; i++) {
      const answer = document.getElementById(`answer${i}`).value.trim();
      if (!answer) {
        Swal.fire({
          icon: "warning",
          title: `${i}번 문제의 답안을 입력해주세요`,
          confirmButtonText: "확인",
          confirmButtonColor: "#0d6efd",
        }).then(() => document.getElementById(`answer${i}`).focus());
        return;
      }
    }

    // 모든 검증 통과 시 폼 제출
    examForm.submit();
  });
});
