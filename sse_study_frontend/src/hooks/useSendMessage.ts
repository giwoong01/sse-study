import { SendMessageApi, SendAllMessageApi } from "../api/SendMessageApi.ts";

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

export const useSendAllMessage = () => {
  const handleSendAll = async () => {
    try {
      const response = await SendAllMessageApi();
      console.log(response);
    } catch (error: any) {
      throw new Error(error.response?.data?.message || "메시지 전송에 실패했습니다.");
    }
  };

  return { handleSendAll };
}
