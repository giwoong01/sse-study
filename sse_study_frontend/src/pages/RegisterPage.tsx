import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Button from "../components/Button.tsx";
import { MemberRegisterApi } from "../api/MemberApi.ts";

const RegisterPage: React.FC = () => {
  const navigate = useNavigate();
  const [name, setName] = useState("");
  const [pwd, setPwd] = useState("");
  const [confirmPwd, setConfirmPwd] = useState("");

  const handleLoginClick = () => {
    navigate("/");
  };

  const handleRegister = async (e: React.FormEvent) => {
    e.preventDefault();
    if (pwd !== confirmPwd) {
      alert("비밀번호가 일치하지 않습니다.");
      return;
    }

    try {
      const response = await MemberRegisterApi(name, pwd);

      if (response) {
        alert("회원가입이 완료되었습니다.");
        navigate("/");
      }
    } catch (error) {
      console.error("회원가입 실패:", error);
    }
  };

  return (
    <Container>
      <h2>회원가입 페이지</h2>
      <Form onSubmit={handleRegister}>
        <Label>
          <LabelText>이름</LabelText>
          <Input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
        </Label>
        <Label>
          <LabelText>비밀번호</LabelText>
          <Input
            type="password"
            value={pwd}
            onChange={(e) => setPwd(e.target.value)}
          />
        </Label>
        <Label>
          <LabelText>비밀번호 확인</LabelText>
          <Input
            type="password"
            value={confirmPwd}
            onChange={(e) => setConfirmPwd(e.target.value)}
          />
        </Label>
        <Button type="submit">회원가입</Button>
      </Form>
      <Button onClick={handleLoginClick}>로그인 페이지로 이동</Button>
    </Container>
  );
};

export default RegisterPage;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #f0f0f0;
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  width: 18.75rem;
  padding: 1.25rem;
  background-color: #fff;
  border-radius: 0.5rem;
  box-shadow: 0 0 0.625rem rgba(0, 0, 0, 0.1);
`;

const Label = styled.label`
  margin-bottom: 0.625rem;
  font-weight: bold;
  width: 100%;
`;

const LabelText = styled.p`
  margin: 0;
  margin-bottom: 0.3125rem;
  font-size: 1rem;
  color: #333;
`;

const Input = styled.input`
  padding: 0.625rem;
  margin-bottom: 0.25rem;
  border: 0.0625rem solid #ccc;
  border-radius: 0.25rem;
  width: 93%;
`;
