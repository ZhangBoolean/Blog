mysql����
1��mysql�У�������ʵ��һ����С�ĵĲ��ɷָ�Ĺ�����λ�������ܹ���֤һ��ҵ���������

































2��������Ĵ�������
ԭ���ԣ���������С�ĵ�λ���������ٷָ�
һ���ԣ�����Ҫ��ͬһ�����е�sql��䣬���뱣֤ͬʱ�ɹ����߹�ʽʧ��
�����ԣ�����1������2 ֮���Ǿ��и����Ե�
�־��ԣ�����һ��������commit��rollback��,�Ͳ����Է���

��������	
1���޸�Ĭ���ύ set autocommit=0;
2��begin
3��start transaction;
�����ֶ��ύ��
commit;
�����ֶ��ع���
rollback;


read uncommitted

read committed

repeatable read

serializable



