import { useState } from "react";
import Link from "next/link";

function Home() {
  // 로그아웃 버튼 시 로컬 스토리지 로그인 정보 삭제합니다.
  const handleLogout = async () => {
    try {
      const accessToken = JSON.parse(localStorage.getItem("loginData")).accessToken;
      const response = await fetch("http://localhost:8080/members/logout", {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
        body: JSON.stringify({ 
          refreshToken: localStorage.getItem('loginData').refreshToken, 
        }),
      });
      if (response.ok) {
        localStorage.removeItem("loginData");
      } else {
        console.error(response.statusText);
      }
    } catch (error) {
      console.error("Error during logout:", error);
    }
  };

  return (
    <div>
      <h1>Hello, Next.js</h1>
      <Link href="/about">
        <div>About</div>
      </Link>
      <br />
      <Link href="/login">
        <div>Login</div>
      </Link>
      <br />
      <button onClick={handleLogout}>Logout</button>
    </div>
  );
}

export default Home;
