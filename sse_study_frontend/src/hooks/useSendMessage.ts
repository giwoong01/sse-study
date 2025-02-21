import { SendMessageApi } from "../api/SendMessageApi.ts";

export const useSendMessage = () => {
  const handleSend = async (targetMemberId: number) => {
    try {
      const response = await SendMessageApi(targetMemberId);
      console.log(response);
    } catch (error: any) {
      throw new Error(error.response?.data?.message || "메시지 전송에 실패했습니다.");
    }
  };

  return { handleSend };
};
