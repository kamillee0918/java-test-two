/**
 * Java 시험 응시 기록 삭제 페이지 유효성 검증 스크립트
 * SweetAlert2를 사용하여 에러 메시지 표시
 */
document.addEventListener("DOMContentLoaded", function () {
  // 서버 측 에러 메시지가 있을 경우 SweetAlert2로 표시
  const errorElement = document.querySelector(".alert-danger span");
  if (errorElement) {
    const errorMessage = errorElement.textContent.trim();
    Swal.fire({
      icon: "error",
      title: "응시 기록 삭제 실패",
      text: errorMessage,
      confirmButtonText: "확인",
      confirmButtonColor: "#0d6efd",
    });
  }

  // 폼 제출 시 비밀번호 입력 검증
  const deleteForm = document.getElementById("deleteForm");
  deleteForm.addEventListener("submit", function (e) {
    const password = document.getElementById("password").value.trim();
    
    // 비밀번호 검증
    if (!password) {
      e.preventDefault();
      Swal.fire({
        icon: "warning",
        title: "비밀번호를 입력해주세요",
        confirmButtonText: "확인",
        confirmButtonColor: "#0d6efd",
      }).then(() => document.getElementById("password").focus());
    }
  });
});
