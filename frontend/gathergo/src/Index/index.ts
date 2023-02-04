// import {} from "./store.ts";
// document.addEventListener('click', (e: Event) => {
//   const target = e.target as Element;
//   const profileIcon = target?.closest('.profile-icon');
//   const sidebarBg = target?.closest('#sidebar-bg');
//   if (profileIcon) {
//     sidebarActiveEvent();
//     dropDownCloseEvent();
//   }
//   if (sidebarBg) {
//     sidebarCloseEvent();
//   }
// });

// function sidebarActiveEvent() {
//   document.querySelector<HTMLDivElement>('#sidebar')?.classList.add('active');
//   //TODO: 모든 dropDown 닫아주기
//   // https://jess2.xyz/css/fade-in-out/
// }
// function sidebarCloseEvent() {
//   document
//     .querySelector<HTMLDivElement>('#sidebar')
//     ?.classList.remove('active');
// }
function dropDownEvent() {
  document.addEventListener('click', (e: Event) => {
    const target = e.target as Element;

    //다른 화면 터치시 모든 dropDown 닫기 event
    const dropDown = target?.closest('.nav-item.dropdown') as HTMLLIElement;
    if (dropDown === null) {
      dropDownCloseAll();
      return;
    }

    //드롭다운 토글 선택시 열고 닫기 event
    const dropDownToggle =
      dropDown.querySelector<HTMLAnchorElement>('.dropdown-toggle');
    if (dropDownToggle === null) return;
    if (dropDownToggle?.classList.contains('show')) dropDownClose(dropDown);
    else dropDownOpen(dropDown);

    //item 선택
    const dropDownItem = target?.closest('.dropdown-item') as HTMLLIElement;
    if (dropDownItem === null) return;

    if (dropDownItem.classList.contains('category')) {
      const index: number = getElementIndex(dropDownItem);
      dropDownToggle.innerHTML = category[index + 1];
    } else dropDownToggle.innerHTML = dropDownItem?.innerHTML;
  });
}

function dropDownCloseAll() {
  document
    .querySelectorAll<HTMLLIElement>('.nav-item.dropdown')
    .forEach((element) => dropDownClose(element));
}

function dropDownOpen(dropDownElement: HTMLLIElement) {
  dropDownCloseAll();
  const toggleElement =
    dropDownElement.querySelector<HTMLAnchorElement>('.dropdown-toggle');
  if (toggleElement?.classList.contains('show')) return;
  toggleElement?.classList.add('show');
  toggleElement?.setAttribute('aria-expanded', 'true');

  const menuElement =
    dropDownElement.querySelector<HTMLAnchorElement>('.dropdown-menu');
  if (menuElement == null) return;
  menuElement.classList.add('show');
  menuElement.setAttribute('data-bs-popper', 'static');
}

function dropDownClose(dropDownElement: HTMLLIElement) {
  const toggleElement =
    dropDownElement.querySelector<HTMLAnchorElement>('.dropdown-toggle');
  if (!toggleElement?.classList.contains('show')) return;
  toggleElement?.classList.remove('show');
  toggleElement?.setAttribute('aria-expanded', 'false');

  const menuElement =
    dropDownElement.querySelector<HTMLAnchorElement>('.dropdown-menu');
  menuElement?.classList.remove('show');
  menuElement?.removeAttribute('data-bs-popper');
}

//header search바 event
function searchBarEvent() {
  const header = document.querySelector<HTMLDivElement>('.header');
  const searchContainer =
    document.querySelector<HTMLDivElement>('.search-container');

  window.onscroll = function () {
    if (header !== null && header.getBoundingClientRect().bottom <= 0) {
      searchContainer?.classList.add('show');
    } else {
      searchContainer?.classList.remove('show');
    }
  };
}
type dropDownType = {
  [key: number]: string;
};
const regionSi: dropDownType = {
  1: '서울특별시',
  2: '부산광역시',
  3: '대구광역시',
  4: '인천광역시',
  5: '광주광역시',
  6: '대전광역시',
  7: '울산광역시',
  8: '세종특별자치시',
  9: '경기도',
  10: '강원도',
  11: '충청북도',
  12: '충청남도',
  13: '전라북도',
  14: '전라남도',
  15: '경상북도',
  16: '경상남도',
  17: '제주특별자치도',
  0: '지역을 선택하세요',
};
const category: dropDownType = {
  1: '아웃도어 / 여행',
  2: '운동 / 스포츠',
  3: '인문학 / 책/ 글',
  4: '업종 / 직무',
  5: '외국 / 언어',
  6: '문화 / 공연 / 축제',
  7: '음악 / 악기',
  8: '패션 / 뷰티',
  9: '공예 / 만들기',
  10: '댄스 / 무용',
  11: '봉사활동',
  12: '사교 / 인맥',
  13: '차 / 오토바이',
  14: '사진 / 영상',
  15: '야구관람',
  16: '게임 / 오락',
  17: '요리 / 제조',
  18: '반려동물',
  19: '자유주제',
  0: '카테고리를 선택하세요',
};
searchBarEvent();
dropDownEvent();

const getElementIndex = (element: HTMLElement) => {
  let index = 0;
  let sibling = element.previousElementSibling;
  while (sibling) {
    index++;
    sibling = sibling.previousElementSibling;
  }
  return index;
};
