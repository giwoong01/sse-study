import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { MemberLoginApi } from "../api/MemberApi.ts";
import styled from "styled-components";
import Button from "../components/Button.tsx";

const LoginPage: React.FC = () => {
  const navigate = useNavigate();
  const [name, setName] = useState("");
  const [pwd, setPwd] = useState("");

  const handleRegisterClick = () => {
    navigate("/register");
  };

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await MemberLoginApi(name, pwd);

      if (response) {
        navigate("/home");
      }
    } catch (error) {
      console.error("로그인 실패:", error);
    }
  };

  return (
    <Container>
      <h2>로그인 페이지</h2>
      <Form onSubmit={handleLogin}>
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
        <Button type="submit">로그인</Button>
      </Form>
      <Button onClick={handleRegisterClick}>회원가입 하러가기!</Button>
    </Container>
  );
};

export default LoginPage;

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
  padding: 1rem;
  background-color: #fff;
  border-radius: 0.5rem;
  box-shadow: 0 0 0.625rem rgba(0, 0, 0, 0.1);
`;

const Label = styled.label`
  font-weight: bold;
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
