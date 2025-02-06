import React from "react";
import styled from "styled-components";

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  children: React.ReactNode;
  variant?: string;
}

const Button: React.FC<ButtonProps> = ({ children, variant, ...props }) => {
  return (
    <StyledButton $variant={variant} {...props}>
      {children}
    </StyledButton>
  );
};

export default Button;

const StyledButton = styled.button<{ $variant?: string }>`
  padding: 0.625rem;
  background-color: #007bff;
  color: #fff;
  margin-top: 0.625rem;
  border: none;
  border-radius: 0.25rem;
  cursor: pointer;

  background-color: ${({ $variant }) =>
    $variant === "danger" ? "#ff0000" : "#007bff"};

  &:hover {
    background-color: ${({ $variant }) =>
      $variant === "danger" ? "#cc0000" : "#0056b3"};
  }
`;
