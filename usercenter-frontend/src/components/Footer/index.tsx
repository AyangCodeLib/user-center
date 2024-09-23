import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';

const Footer: React.FC = () => {
  const defaultMessage = '阿洋努力学习';
  const currentYear = new Date().getFullYear();
  return (
    <DefaultFooter
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'yuque garden',
          title: '语雀知识花园',
          href: 'https://www.yuque.com/ayangnulixiulian',
          blankTarget: true,
        },
        {
          key: 'github',
          title: (
            <>
              <GithubOutlined /> 阿洋努力学习 GitHub{' '}
            </>
          ),
          href: 'https://github.com/AyangCodeLib',
          blankTarget: true,
        },
      ]}
    />
  );
};
export default Footer;
